package space.maxus.plib.registry;

import space.maxus.plib.model.ItemModel;

public final class ItemRegistry extends SimpleRegistry<ItemModel> {
    @Override
    protected void externalRegistration(Identifier id, ItemModel value) {

    }

    @Override
    protected ItemModel externalFind(Identifier id) {
        return registrants.get(id);
    }
}
