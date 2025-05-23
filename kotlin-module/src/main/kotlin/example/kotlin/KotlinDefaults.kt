package example.kotlin

data class KotlinFile(val name: String,
                      val extension: String,
                      val location: String,
                      val executable: Boolean)

@JvmRecord
data class KotlinRecordFile(val name: String,
                            val extension: String,
                            val location: String,
                            val executable: Boolean)











data class KotlinMutFile(var name: String,
                         var extension: String,
                         var location: String,
                         var executable: Boolean)




object KotlinDefaults {
    @JvmStatic
    @JvmOverloads
    fun createFile(name: String = "removeme",
                   extension: String = "",
                   location: String = "/tmp",
                   executable : Boolean = false): KotlinFile {
        return KotlinFile(name, extension, location, executable)
    }

    @JvmStatic
    fun createFileWithoutOverloads(name: String = "removeme",
                                   extension: String = "",
                                   location: String = "/tmp",
                                   executable : Boolean = false): KotlinFile {
        return KotlinFile(name, extension, location, executable)
    }

    fun use() {
        createFile()
        createFile("named", "txt")
        createFile(location = "/var/tmp")

        createFileWithoutOverloads()
        createFileWithoutOverloads("named", "txt")
        createFileWithoutOverloads(location = "/var/tmp")



        // not yet possible
//        println("Kotlin calls Java with named parameters: " +
//                example.java.JavaManyParameters.createFile(
//                        location = "/tmp", name = "removeme",
//                        executable = false, extension = ".txt"))
    }
}
