import java.nio.file.Path

import static java.nio.file.Files.*
import static java.nio.file.Paths.get

def props = [:]

props.group = ask("Define value for 'group' [com.pomkine]: ", "com.pomkine", "group")
props.version = ask("Define value for 'version' [0.0.1]: ", "0.0.1", "version")
props.projectName = projectDir.name
String mainApplicationFileName = 'Application.groovy'

processTemplates "build.gradle", props
processTemplates 'README.md', props
processTemplates mainApplicationFileName, props

String pkgPath = props.group.replace('.' as char, '/' as char)
Path templatePath = templateDir.toPath()
Path projectPath = projectDir.toPath()

Path groovySourceDirWithPackage = get projectPath as String, 'src/main/groovy/', pkgPath
Path destinationAppFilePath = groovySourceDirWithPackage.resolve mainApplicationFileName
Path templateApplicationPath = templatePath.resolve mainApplicationFileName

groovySourceDirWithPackage.toFile() mkdirs()

try {
    move templateApplicationPath, destinationAppFilePath

    Path newSettingsPath = projectPath.parent.resolve('settings.gradle')
    File newSettingsFile = newSettingsPath.toFile()

    if (notExists(newSettingsPath)) {
        newSettingsFile = createFile newSettingsPath toFile()
    }

    def currentConfigDump = newSettingsFile.text

    newSettingsFile.withWriter { writer ->
        currentConfigDump.eachLine { line ->
            if (line.contains('end includes')) {
                writer << "include '${projectDir.name}'"
                writer << '\n'
            }

            writer << line
            writer << '\n'

        }
        writer.close()
    }
} catch (ignored) {
    ignored.printStackTrace()
    println '^' * 50
    println 'ignored exception'.center(50, '^')
    println '^' * 50
}