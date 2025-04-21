package org.shintoinari.memberarchivegenerator.app

class DefaultApplication(
    override val config: Application.Config
) : Application {

    /**
     * The main application workflow:
     * 1. Read into the domain models,
     * 2. Write the domain models out.
     */
    override suspend fun run() {
    }

}