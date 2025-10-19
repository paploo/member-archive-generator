package org.shintoinari.memberarchivegenerator.writer.templates

import org.shintoinari.memberarchivegenerator.data.Video
import org.shintoinari.memberarchivegenerator.data.VideoGroup
import org.shintoinari.memberarchivegenerator.util.ChronoFormatter
import org.shintoinari.memberarchivegenerator.util.en
import org.shintoinari.memberarchivegenerator.util.format
import org.shintoinari.memberarchivegenerator.util.jp
import org.shintoinari.memberarchivegenerator.writer.VideoGroupsWriter
import java.util.UUID
import kotlin.text.replaceIndent

open class WordPressSourceTemplate : CallbackTemplate() {

    override fun startPage(
        context: VideoGroupsWriter.Context,
        groups: Collection<VideoGroup>
    ): String = """
        <!-- wp:paragraph {"align":"center"} -->
        <p class="has-text-align-center"><strong>Welcome to the Shinto Inari Kai member's video archive!</strong></p>
        <!-- /wp:paragraph -->
    """.trimIndent() + "\n\n"

    override fun endPage(
        context: VideoGroupsWriter.Context,
        groups: Collection<VideoGroup>
    ): String = ""

    override fun startGroup(
        context: VideoGroupsWriter.Context,
        group: VideoGroup
    ): String = """
        <!-- wp:heading -->
        <h2 class="wp-block-heading">${group.year}年</h2>
        <!-- /wp:heading -->
        
        <!-- wp:table -->
        <figure class="wp-block-table is-style-stripes">
            <table class="has-fixed-layout">
                <thead>
                    <tr>
                        <th class="has-text-align-center" data-align="center"></th>
                        <th class="has-text-align-center" data-align="center">Ceremony Video</th>
                        <th class="has-text-align-center" data-align="center">Closing Talk Video</th>
                    </tr>
                </thead>
                <tbody>
    """.trimIndent() + "\n"

    override fun endGroup(
        context: VideoGroupsWriter.Context,
        group: VideoGroup
    ): String = """
                </tbody>
            </table>
        </figure>
        <!-- /wp:table -->
    """.trimIndent() + "\n\n"

    override fun startRow(
        context: VideoGroupsWriter.Context,
        row: VideoGroup.Row
    ): String = """
        <tr>
            <td class="has-text-align-center" data-align="center">
                <strong>${row.date.format(ChronoFormatter.en)}</strong><br><strong>${row.date.format(ChronoFormatter.jp)}</strong>
            </td>
    """.replaceIndent(indent(tableRowIndent)) + "\n"

    override fun endRow(
        context: VideoGroupsWriter.Context,
        row: VideoGroup.Row
    ): String = """
        </tr>
    """.replaceIndent(indent(tableRowIndent)) + "\n"

    override fun videoCell(
        context: VideoGroupsWriter.Context,
        video: Video
    ): String = """
        <td class="has-text-align-center" data-align="center">
            <a href="${video.youTubeLink}" target="_blank" rel="noreferrer noopener"><img class="wp-image-6793" style="width: 50px;" src="$youTubeIconSource" alt="Youtube video play icon"></a>
            <br><strong>${listOfNotNull(video.titleEn, video.titleJp).joinToString("<br>")}</strong>
        </td>
    """.replaceIndent(indent(tableRowIndent + 1)) + "\n"

    override fun emptyCell(
        context: VideoGroupsWriter.Context
    ): String = """
        <td class="has-text-align-center" data-align="center"></td>
    """.replaceIndent(indent(tableRowIndent + 1)) + "\n"


    private fun indent(level: Int): String = "    ".repeat(level)

    protected open val tableRowIndent = 3

    private val Video.youTubeLink: String
        get() = "https://www.youtube.com/watch?v=$youTubeId"

    private val youTubeIconSource =
        "https://shintoinari.org/wp-content/uploads/2024/05/YouTube_play_button_icon_128.png"

}

/**
 * Alternate version of WordPressSourceTemplate using accordions for groups.
 *
 * This stopped working but looked nicer.
 *
 * TODO: dont inherit. Instead move to builders that supplies the various blocks and elements so we can combine more arbitrarily.
 */
class WordPressSourceAccordionGroupTemplate : WordPressSourceTemplate() {

    override fun startGroup(
        context: VideoGroupsWriter.Context,
        group: VideoGroup
    ): String = """
        <!-- wp:advgb/accordions {"id":"advgb-accordions-${group.accordionId}","changed":true,"rootBlockId":"${group.accordionId}"} -->
        <div class="wp-block-advgb-accordions advgb-accordions-${group.accordionId} advgb-accordion-wrapper">
            <!-- wp:advgb/accordion-item {"header":"${group.year}年","changed":true,"rootBlockId":"${group.accordionId}"} -->
            <div class="wp-block-advgb-accordion-item advgb-accordion-item" style="margin-bottom:15px">
                <div class="advgb-accordion-header"
                    style="background-color:#000;color:#eee;border-style:solid;border-width:1px;border-radius:2px"><span
                        class="advgb-accordion-header-icon"><svg fill="#fff" xmlns="http://www.w3.org/2000/svg" width="24"
                            height="24" viewBox="0 0 24 24">
                            <path fill="none" d="M0,0h24v24H0V0z"></path>
                            <path
                                d="M12,5.83L15.17,9l1.41-1.41L12,3L7.41,7.59L8.83,9L12,5.83z M12,18.17L8.83,15l-1.41,1.41L12,21l4.59-4.59L15.17,15 L12,18.17z">
                            </path>
                        </svg></span>
                    <h4 class="advgb-accordion-header-title" style="color:inherit">${group.year}年</h4>
                </div>
                <div class="advgb-accordion-body"
                    style="border-style:solid !important;border-width:1px !important;border-color:undefined !important;border-top:none !important;border-radius:2px !important">
                    <!-- wp:table {"className":"is-style-stripes","customStyle":"","identifyColor":""} -->
                    <figure class="wp-block-table is-style-stripes">
                        <table class="has-fixed-layout">
                            <thead>
                                <tr>
                                    <th class="has-text-align-center" data-align="center"></th>
                                    <th class="has-text-align-center" data-align="center">Ceremony Video</th>
                                    <th class="has-text-align-center" data-align="center">Closing Talk Video</th>
                                </tr>
                            </thead>
                            <tbody>
    """.trimIndent() + "\n"

    override fun endGroup(
        context: VideoGroupsWriter.Context,
        group: VideoGroup
    ): String = """
                        </tbody>
                        </table>
                    </figure>
                    <!-- /wp:table -->
                </div>
            </div>
            <!-- /wp:advgb/accordion-item -->
        </div>
        <!-- /wp:advgb/accordions -->
    """.trimIndent() + "\n\n"

    /**
     * A predicatble ID for the accordion.
     */
    private val VideoGroup.accordionId: UUID
        get() = UUID.nameUUIDFromBytes(year.toString().toByteArray())

    override val tableRowIndent = 5

}