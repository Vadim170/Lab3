import ProgramRunner.runErrorLicenseMessage
import ProgramRunner.runProgramm

fun main(args: Array<String>) {
    val licenseController = LicenseController()
    if(args.contains("first")) {
        println("first launch")
        licenseController.writeLicense()
        runProgramm()
    } else {
        println("second launch")
        val isLicense = licenseController.checkLicense()
        if(isLicense) {
            runProgramm()
        } else {
            runErrorLicenseMessage()
        }
    }
}


