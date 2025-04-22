package org.shintoinari.memberarchivegenerator.writer

import org.shintoinari.memberarchivegenerator.data.VideoGroup

interface Writer<in A, out R> : suspend (A) -> Result<R>

typealias VideoGroupsWriter = Writer<List<VideoGroup>, Unit>