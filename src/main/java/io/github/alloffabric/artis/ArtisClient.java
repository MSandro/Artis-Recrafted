package io.github.alloffabric.artis;

import io.github.alloffabric.artis.api.ArtisExistingBlockType;
import io.github.alloffabric.artis.api.ArtisExistingItemType;
import io.github.alloffabric.artis.api.ArtisTableType;
import io.github.alloffabric.artis.inventory.ArtisCraftingScreen;
import io.github.alloffabric.artis.inventory.ArtisRecipeProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArtisClient implements ClientModInitializer {

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        for (ArtisTableType type : Artis.ARTIS_TABLE_TYPES) {
            ScreenHandlerType<ArtisRecipeProvider> screenHandlerType = (ScreenHandlerType<ArtisRecipeProvider>) Registry.SCREEN_HANDLER.get(type.getId());
            ScreenRegistry.<ArtisRecipeProvider, ArtisCraftingScreen>register(screenHandlerType, ArtisCraftingScreen::new);
            
            if (!(type instanceof ArtisExistingBlockType) && !(type instanceof ArtisExistingItemType)) {
                if (type.hasColor()) {
                    ColorProviderRegistry.BLOCK.register((state, world, pos, index) -> type.getColor(), Registry.BLOCK.get(type.getId()));
                    ColorProviderRegistry.ITEM.register((stack, index) -> type.getColor(), Registry.ITEM.get(type.getId()));
                }
                BlockRenderLayerMap.INSTANCE.putBlock(Registry.BLOCK.get(type.getId()), RenderLayer.getCutout());
            }
        }

        ClientSidePacketRegistry.INSTANCE.register(Artis.recipe_sync, (packetContext, attachedData) -> {
            Identifier location = attachedData.readIdentifier();
            packetContext.getTaskQueue().execute(() -> {
                ScreenHandler container = packetContext.getPlayer().currentScreenHandler;
                if (container instanceof ArtisRecipeProvider) {
                    Recipe<?> r = MinecraftClient.getInstance().world.getRecipeManager().get(location).orElse(null);
                    updateLastRecipe((ArtisRecipeProvider) packetContext.getPlayer().currentScreenHandler, (Recipe<CraftingInventory>) r);
                }
            });
        });
    }

    public static void updateLastRecipe(ArtisRecipeProvider container, Recipe<CraftingInventory> rec) {
        CraftingInventory craftInput = container.getCraftInv();
        CraftingResultInventory craftResult = container.getResultInv();

        if (craftInput == null) {
            System.out.println("why are these null?");
        } else {
            craftResult.setLastRecipe(rec);
            if (rec != null) craftResult.setStack(0, rec.craft(craftInput));
            else craftResult.setStack(0, ItemStack.EMPTY);
        }
    }

}
