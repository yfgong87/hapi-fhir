package ca.uhn.fhir.jpa.subscription.resthook;

/*-
 * #%L
 * HAPI FHIR JPA Server
 * %%
 * Copyright (C) 2014 - 2017 University Health Network
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import ca.uhn.fhir.jpa.subscription.BaseSubscriptionInterceptor;

public class SubscriptionRestHookInterceptor extends BaseSubscriptionInterceptor {
	private SubscriptionDeliveringRestHookSubscriber mySubscriptionDeliverySubscriber;

	@Override
	protected void registerDeliverySubscriber() {
		if (mySubscriptionDeliverySubscriber == null) {
			mySubscriptionDeliverySubscriber = new SubscriptionDeliveringRestHookSubscriber(getSubscriptionDao(), getChannelType(), this);
		}
		getDeliveryChannel().subscribe(mySubscriptionDeliverySubscriber);
	}

	@Override
	public org.hl7.fhir.r4.model.Subscription.SubscriptionChannelType getChannelType() {
		return org.hl7.fhir.r4.model.Subscription.SubscriptionChannelType.RESTHOOK;
	}

	@Override
	protected void unregisterDeliverySubscriber() {
		getDeliveryChannel().unsubscribe(mySubscriptionDeliverySubscriber);
	}
}
