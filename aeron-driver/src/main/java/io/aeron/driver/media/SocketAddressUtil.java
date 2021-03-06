/*
 * Copyright 2014-2018 Real Logic Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.aeron.driver.media;

import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

class SocketAddressUtil
{
    private static final Pattern IPV4_ADDRESS_PATTERN = Pattern.compile("([^:]+)(?::([0-9]+))?");
    private static final Pattern IPV6_ADDRESS_PATTERN = Pattern.compile(
        "\\[([0-9A-Fa-f:]+)(?:%[a-zA-Z0-9_.~-]+)?\\](?::([0-9]+))?");

    /**
     * Utility for parsing socket addresses from a {@link CharSequence}.  Supports
     * hostname:port, ipV4Address:port and [ipV6Address]:port
     *
     * @param cs to be parsed for the socket address.
     * @return An {@link InetSocketAddress} for the parsed input.
     */
    static InetSocketAddress parse(final CharSequence cs)
    {
        if (null == cs)
        {
            throw new NullPointerException("Input string must not be null");
        }

        final Matcher ipV4Matcher = IPV4_ADDRESS_PATTERN.matcher(cs);

        if (ipV4Matcher.matches())
        {
            final String host = ipV4Matcher.group(1);
            final String port = ipV4Matcher.group(2);

            return newSocketAddress(host, port);
        }

        final Matcher ipV6Matcher = IPV6_ADDRESS_PATTERN.matcher(cs);

        if (ipV6Matcher.matches())
        {
            final String host = ipV6Matcher.group(1);
            final String port = ipV6Matcher.group(2);

            return newSocketAddress(host, port);
        }

        throw new IllegalArgumentException("Invalid format: " + cs);
    }

    private static InetSocketAddress newSocketAddress(final String host, final String port)
    {
        if (null == port)
        {
            throw new IllegalArgumentException("The 'port' portion of the address is required");
        }

        return new InetSocketAddress(host, parseInt(port));
    }
}
