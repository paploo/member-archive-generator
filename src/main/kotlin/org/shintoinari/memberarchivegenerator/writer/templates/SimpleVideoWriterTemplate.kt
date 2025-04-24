package org.shintoinari.memberarchivegenerator.writer.templates

import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter

class SimpleVideoWriterTemplate : CallbackTemplate() {

    override fun startPage(
        context: VideoGroupsWriter.Context,
        groups: Collection<VideoGroup>
    ): String = "===== Video Archive =====\n\n"

    override fun startGroup(
        context: VideoGroupsWriter.Context,
        group: VideoGroup
    ): String = "${group.year}\n"

    override fun startRow(
        context: VideoGroupsWriter.Context,
        row: VideoGroup.Row
    ): String = "     ${row.date}    "

    override fun endRow(
        context: VideoGroupsWriter.Context,
        row: VideoGroup.Row
    ): String = "\n"

    override fun videoCell(
        context: VideoGroupsWriter.Context,
        video: Video
    ): String = video.titleEn.toString().padEnd(80)

    override fun emptyCell(
        context: VideoGroupsWriter.Context
    ): String = ".".padEnd(80)

}