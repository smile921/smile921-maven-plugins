# Mojo 插件学习编写

## 熟悉maven的插件系统，学习扩展自己想要的插件

## 实现一个插件把项目的所以依赖包 按 repo 目录结构 copy 到 target/repo 目录
实现效果与 下面的配置相同
> mvn clean dependency:copy-dependencies
```xml
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-dependency-plugin</artifactId>
				<version>3.0.2</version>
				<configuration>
					<useRepositoryLayout>true</useRepositoryLayout>
					<copyPom>true</copyPom>
					<addParentPoms>true</addParentPoms>
				</configuration>
			</plugin>
```			
###  使用 mvn clean org.smile921:smile921-maven-plugin:genrepo