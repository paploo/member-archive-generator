package org.shintoinari.memberarchivegenerator.writer.templates

import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter

class DebugVideoWriterTemplate : CallbackTemplate() {

    override fun startGroup(
        context: VideoGroupsWriter.Context,
        group: VideoGroup
    ): String = "${group.year}\n"

    override fun startRow(
        context: VideoGroupsWriter.Context,
        row: VideoGroup.Row
    ): String = "\t${row.date}\n"

    override fun videoCell(
        context: VideoGroupsWriter.Context,
        video: Video
    ): String = "\t\t${video}\n"

}