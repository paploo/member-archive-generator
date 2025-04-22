package org.shintoinari.memberarchivegenerator.writer.templates

import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.writer.TemplatedVideoGroupsWriter
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter

open class CallbackTemplate : TemplatedVideoGroupsWriter.Template {
    open fun startPage(context: VideoGroupsWriter.Context, groups: Collection<VideoGroup>): String = ""
    open fun endPage(context: VideoGroupsWriter.Context, groups: Collection<VideoGroup>): String = ""

    open fun startGroup(context: VideoGroupsWriter.Context, group: VideoGroup): String = ""
    open fun endGroup(context: VideoGroupsWriter.Context, group: VideoGroup): String = ""

    open fun startRow(context: VideoGroupsWriter.Context, row: VideoGroup.Row): String = ""
    open fun endRow(context: VideoGroupsWriter.Context, row: VideoGroup.Row): String = ""

    open fun videoCell(context: VideoGroupsWriter.Context, video: Video): String = ""
    open fun emptyCell(context: VideoGroupsWriter.Context): String = ""

    final override fun build(
        context: VideoGroupsWriter.Context,
        groups: Collection<VideoGroup>
    ): String =
        StringBuffer().let { buffer ->
            when(context.mode) {
                VideoGroupsWriter.OutputMode.FullPage -> buffer.append(startPage(context, groups)).append(startPage(context, groups))
                VideoGroupsWriter.OutputMode.YearBlocksOnly -> buffer
            }

            groups.forEach { group ->
                buffer.append(startGroup(context, group))
                group.rows.forEach { row ->
                    buffer.append(startRow(context, row))
                    listOf(row.saiVideo, row.closingVideo).forEach { video ->
                        buffer.append(
                            video?.let { videoCell(context, it) } ?: emptyCell(context)
                        )
                    }
                    buffer.append(endRow(context, row))
                }
                buffer.append(endGroup(context, group))
            }

            when(context.mode) {
                VideoGroupsWriter.OutputMode.FullPage -> buffer.append(startPage(context, groups)).append(endPage(context, groups))
                VideoGroupsWriter.OutputMode.YearBlocksOnly -> buffer
            }
        }.toString()
}