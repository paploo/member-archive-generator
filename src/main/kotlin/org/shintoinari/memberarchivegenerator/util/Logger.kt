package org.shintoinari.memberarchivegenerator.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Extension property to get a properly configured logger for any class.
 */
val <T : Any> T.logger: Logger get() = LoggerFactory.getLogger(this::class.java)