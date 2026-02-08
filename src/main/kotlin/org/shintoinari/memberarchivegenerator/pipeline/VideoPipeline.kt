package org.shintoinari.memberarchivegenerator.pipeline

import org.shintoinari.memberarchivegenerator.reader.VideosReader
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter

/**
 * Standardized interface for application pipelines.
 *
 * Application Pipelines directly use the application configuration and produce
 * the result side-effects. Any failures are caught and communicated back using the
 * returned `Result`.
 */
interface VideoPipeline<Input> : suspend (Input) -> Result<Unit>

