/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.knowdata.framework.core.util.encipher;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * A wrapper of {@link java.util.Base64} with convenient conversion methods between {@code byte[]} and {@code String}
 */
public final class Base64Utils {
    private static final java.util.Base64.Decoder DECODER = java.util.Base64.getDecoder();
    private static final java.util.Base64.Encoder ENCODER = java.util.Base64.getEncoder();

    private Base64Utils() {

    }

    public static byte[] encode(byte[] src) {
        return src.length == 0 ? src : Base64.getEncoder().encode(src);
    }

    public static byte[] decode(byte[] src) {
        return src.length == 0 ? src : Base64.getDecoder().decode(src);
    }

    /**
     * 密文解码为明文
     * @param cipherText
     * @return originText
     */
    public static String decode(String cipherText) {
        return decode( cipherText, StandardCharsets.UTF_8);
    }

    public static String decode(String cipherText, Charset charset) {
        return new String(DECODER.decode(cipherText), charset);
    }

    /**
     * 明文编码为密文
     * @param originText
     * @return cipherText
     */
    public static String encode(String originText) {
        return encode(originText, StandardCharsets.UTF_8);
    }
    public static String encode(String originText, Charset charset) {
        return ENCODER.encodeToString(originText.getBytes(charset));
    }


}
