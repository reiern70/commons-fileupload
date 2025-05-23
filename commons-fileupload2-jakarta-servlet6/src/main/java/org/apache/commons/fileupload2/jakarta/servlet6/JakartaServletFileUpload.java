/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.fileupload2.jakarta.servlet6;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload2.core.AbstractFileUpload;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.core.FileItemFactory;
import org.apache.commons.fileupload2.core.FileItemInputIterator;
import org.apache.commons.fileupload2.core.FileUploadException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * High level API for processing file uploads.
 * <p>
 * This class handles multiple files per single HTML widget, sent using {@code multipart/mixed} encoding type, as specified by
 * <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a>. Use {@link #parseRequest(HttpServletRequest)} to acquire a list of {@link FileItem}s associated
 * with a given HTML widget.
 * </p>
 * <p>
 * How the data for individual parts is stored is determined by the factory used to create them; a given part may be in memory, on disk, or somewhere else.
 * </p>
 *
 * @param <I> The FileItem type.
 * @param <F> the FileItemFactory type.
 */
public class JakartaServletFileUpload<I extends FileItem<I>, F extends FileItemFactory<I>> extends AbstractFileUpload<HttpServletRequest, I, F> {

    /**
     * Constant for HTTP POST method.
     */
    private static final String POST_METHOD = "POST";

    /**
     * Tests whether the request contains multipart content.
     *
     * @param request The servlet request to be evaluated. Must be non-null.
     * @return {@code true} if the request is multipart; {@code false} otherwise.
     */
    public static final boolean isMultipartContent(final HttpServletRequest request) {
        return POST_METHOD.equalsIgnoreCase(request.getMethod()) && AbstractFileUpload.isMultipartContent(new JakartaServletRequestContext(request));
    }

    /**
     * Constructs an uninitialized instance of this class. A factory must be configured, using {@code setFileItemFactory()}, before attempting to parse
     * requests.
     *
     * @see AbstractFileUpload#AbstractFileUpload()
     */
    public JakartaServletFileUpload() {
    }

    /**
     * Constructs an instance of this class which uses the supplied factory to create {@code FileItem} instances.
     *
     * @see AbstractFileUpload#AbstractFileUpload()
     * @param fileItemFactory The factory to use for creating file items.
     */
    public JakartaServletFileUpload(final F fileItemFactory) {
        setFileItemFactory(fileItemFactory);
    }

    /**
     * Gets an <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a> compliant {@code multipart/form-data} file item iterator.
     *
     * @param request The servlet request to be parsed.
     * @return An iterator to instances of {@code FileItemInput} parsed from the request, in the order that they were transmitted.
     * @throws FileUploadException if there are problems reading/parsing the request or storing files.
     * @throws IOException         An I/O error occurred. This may be a network error while communicating with the client or a problem while storing the
     *                             uploaded content.
     */
    @Override
    public FileItemInputIterator getItemIterator(final HttpServletRequest request) throws FileUploadException, IOException {
        return super.getItemIterator(new JakartaServletRequestContext(request));
    }

    /**
     * Parses an <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a> compliant {@code multipart/form-data} stream.
     *
     * @param request The servlet request to be parsed.
     * @return A map of {@code FileItem} instances parsed from the request.
     * @throws FileUploadException if there are problems reading/parsing the request or storing files.
     */
    @Override
    public Map<String, List<I>> parseParameterMap(final HttpServletRequest request) throws FileUploadException {
        return parseParameterMap(new JakartaServletRequestContext(request));
    }

    /**
     * Parses an <a href="http://www.ietf.org/rfc/rfc1867.txt">RFC 1867</a> compliant {@code multipart/form-data} stream.
     *
     * @param request The servlet request to be parsed.
     * @return A list of {@code FileItem} instances parsed from the request, in the order that they were transmitted.
     * @throws FileUploadException if there are problems reading/parsing the request or storing files.
     */
    @Override
    public List<I> parseRequest(final HttpServletRequest request) throws FileUploadException {
        return parseRequest(new JakartaServletRequestContext(request));
    }

}
