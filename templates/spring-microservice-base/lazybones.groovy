def props = [:]

props.group = ask("Define value for 'group' [com.pomkine]: ", "com.pomkine", "group")
props.version = ask("Define value for 'version' [0.0.1]: ", "0.0.1", "version")
props.projectName=ask("Define value for 'projectName': ", "default", "projectName")

processTemplates "build.gradle", props
processTemplates "settings.gradle", props
