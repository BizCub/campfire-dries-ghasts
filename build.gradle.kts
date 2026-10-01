plugins {
    id("io.github.bizcub.multiloader")
}

multiloader {
    setMREnvironment(mrEnvs.serverOnly)
    setCFEnvironment(cfEnvs.server)

    versionRange(version = "26.1.2", to = "latest")
    versionRange(version = "1.21.8", to = "1.21.10")

    if (isFabric) {
        addDependency(
            dependency = "net.fabricmc:fabric-loader:${getDep("fabric")}"
        )
    }
}
