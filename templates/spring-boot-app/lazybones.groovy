import java.nio.file.Path

import static java.nio.file.Files.move
import static java.nio.file.Paths.get

def props = [:]

props.group = ask("Define value for 'group' [com.pomkine]: ", "com.pomkine", "group")
props.version = ask("Define value for 'version' [0.0.1]: ", "0.0.1", "version")
props.projectName = ask("Define value for 'projectName': ", "default", "projectName")
String mainApplicationFileName = 'Application.java'
String applicationConfigFileName = 'src/main/resources/application.yml'
String readmeFileName = 'README.md'

processTemplates "build.gradle", props
processTemplates "settings.gradle", props
processTemplates mainApplicationFileName, props
processTemplates applicationConfigFileName, props
processTemplates readmeFileName, props

String pkgPath = props.group.replace('.' as char, '/' as char)
Path templatePath = templateDir.toPath()
Path projectPath = projectDir.toPath()

Path javaSourceDirWithPackage = get projectPath as String, 'src/main/java/', pkgPath
Path destinationAppFilePath = javaSourceDirWithPackage.resolve mainApplicationFileName
Path templateApplicationPath = templatePath.resolve mainApplicationFileName

javaSourceDirWithPackage.toFile().mkdirs()


try {
    move templateApplicationPath, destinationAppFilePath
} catch (ignored) {
    ignored.printStackTrace()
    println '^' * 50
    println 'ignored exception'.center(50, '^')
    println '^' * 50
}
