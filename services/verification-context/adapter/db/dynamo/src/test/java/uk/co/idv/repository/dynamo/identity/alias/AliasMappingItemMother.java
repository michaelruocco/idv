package uk.co.idv.repository.dynamo.identity.alias;

import com.amazonaws.services.dynamodbv2.document.Item;

public class AliasMappingItemMother {

    public static Item build() {
        return build(0);
    }

    public static Item build(final int number) {
        return new Item()
                .withPrimaryKey("alias", "alias" + number)
                .with("idvId", "cfc54f11-6d36-4687-84b8-d64f81e8431b");
    }

}
