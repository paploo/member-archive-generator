package org.shintoinari.memberarchivegenerator.reader

import org.shintoinari.memberarchivegenerator.data.Video
import java.nio.file.Path

interface Reader<in A, out R> : suspend (A) -> Result<R>

typealias VideosReader = Reader<Path, List<Video>>