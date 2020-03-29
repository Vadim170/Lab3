import oshi.SystemInfo
import oshi.hardware.CentralProcessor
import oshi.hardware.ComputerSystem
import oshi.hardware.HardwareAbstractionLayer
import oshi.software.os.OperatingSystem
import java.io.*
import java.nio.file.Paths

class LicenseController() {
    val identifier: String = generateLicenseKey()
    val licenseDirrection = getLicenseFileDirrection()
    val licenseFileName = "license.txt"

    private fun getLicenseFileDirrection(): String {
        val path = Paths.get(LicenseController::class.java.getResource(".").toURI())
        return path.parent.toString()
    }


    private fun generateLicenseKey(): String {
        val systemInfo = SystemInfo()
        val operatingSystem: OperatingSystem = systemInfo.getOperatingSystem()
        val hardwareAbstractionLayer: HardwareAbstractionLayer = systemInfo.getHardware()
        val centralProcessor: CentralProcessor = hardwareAbstractionLayer.getProcessor()
        val computerSystem: ComputerSystem = hardwareAbstractionLayer.getComputerSystem()
        val vendor: String = operatingSystem.getManufacturer()
        val processorSerialNumber: String = computerSystem.getSerialNumber()
        val processorIdentifier: String = centralProcessor.getIdentifier()
        val processors: Int = centralProcessor.getLogicalProcessorCount()
        val delimiter = "#"
        return vendor +
                delimiter +
                processorSerialNumber +
                delimiter +
                processorIdentifier +
                delimiter +
                processors
    }

    fun checkLicense() : Boolean {
        var fileReader: FileReader? = null
        try {
            val file = File("$licenseDirrection/$licenseFileName")
            fileReader = FileReader(file)
            val reader = BufferedReader(fileReader)
            val line = reader.readLine() // считаем первую строку
            return line.trim() == identifier.hashCode().toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fileReader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }

    fun writeLicense() {
        var fileWriter: FileWriter? = null
        try {
            val file = File("$licenseDirrection/$licenseFileName")
            fileWriter = FileWriter(file)
            fileWriter.write(identifier.hashCode().toString())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fileWriter?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}