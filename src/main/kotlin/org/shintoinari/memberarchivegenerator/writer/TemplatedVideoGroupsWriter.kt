package org.shintoinari.memberarchivegenerator.writer

import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.writer.templates.SimpleVideoWriterTemplate
import org.shintoinari.memberarchivegenerator.writer.templates.WordPressSourceTemplate

class TemplatedVideoGroupsWriter(
    val template: Template
) : VideoGroupsWriter {

    constructor(format: Format) : this(format.template)

    override suspend fun write(
        context: VideoGroupsWriter.Context,
        argument: List<VideoGroup>
    ): Result<Unit> =
        Result.runCatching {
            template.build(context, argument)
        }.mapCatching { html ->
            context.ioWriter.use { it.write(html) }
        }

    interface Template {
        fun build(context: VideoGroupsWriter.Context, groups: Collection<VideoGroup>): String
    }

    enum class Format(val template: Template, val fileExtension: String) {
        Simple(SimpleVideoWriterTemplate(), "txt"),
        WordPress(WordPressSourceTemplate(), "html");
    }

}
