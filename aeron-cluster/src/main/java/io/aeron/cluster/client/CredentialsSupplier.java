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
package io.aeron.cluster.client;

/**
 * Supplier of credentials for authentication with a cluster leader.
 *
 * Implement this interface to supply credentials for clients. If no credentials are required then the
 * {@link NullCredentialsSupplier} can be used.
 */
public interface CredentialsSupplier
{
    /**
     * Provide encoded credentials to be included in Session Connect message to the cluster.
     *
     * @return encoded credentials to be included in the Session Connect message to the cluster.
     */
    byte[] encodedCredentials();

    /**
     * Given some encoded challenge, provide the credentials to be included in a Challenge Response as part of
     * authentication with a cluster.
     *
     * @param encodedChallenge from the cluster to use in providing a credential.
     * @return a encoded credentials to be included in the Challenge Response to the cluster.
     */
    byte[] onChallenge(byte[] encodedChallenge);
}
