package org.shintoinari.memberarchivegenerator.writer

import org.shintoinari.memberarchivegenerator.data.VideoGroup
import java.io.OutputStreamWriter
import java.io.Writer

class TemplatedVideoGroupsWriter(
    val template: Template,
    val writer: Writer = OutputStreamWriter(System.out),
) : VideoGroupsWriter {

    override suspend fun write(
        context: VideoGroupsWriter.Context,
        argument: List<VideoGroup>
    ): Result<Unit> =
        Result.runCatching {
            template.build(context, argument)
        }.mapCatching { html ->
            writer.use { it.write(html) }
        }

    interface Template {
        fun build(context: VideoGroupsWriter.Context, groups: Collection<VideoGroup>): String
    }

}
