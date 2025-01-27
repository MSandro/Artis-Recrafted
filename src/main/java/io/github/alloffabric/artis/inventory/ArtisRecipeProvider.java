package io.github.alloffabric.artis.inventory;

import io.github.alloffabric.artis.Artis;
import io.github.alloffabric.artis.api.ArtisTableType;
import io.github.alloffabric.artis.api.ContainerLayout;
import io.github.alloffabric.artis.api.RecipeProvider;
import io.github.alloffabric.artis.inventory.slot.WArtisResultSlot;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class ArtisRecipeProvider extends SyncedGuiDescription implements RecipeProvider {
    private final ArtisTableType tableType;
    private final PlayerEntity player;
    private final ArtisCraftingInventory craftInv;
    private final CraftingResultInventory resultInv;
    private final ScreenHandlerContext context;

    private final WPlainPanel mainPanel;
    private final WItemSlot craftingGrid;
    private final WArtisResultSlot resultSlot;
    private final WPlayerInvPanel playerInv;
    private WItemSlot catalystSlot;
    
    public ArtisRecipeProvider(ScreenHandlerType type, ArtisTableType tableType, int syncId, PlayerEntity player, ScreenHandlerContext context) {
        super(type, syncId, player.getInventory(), getBlockInventory(context), getBlockPropertyDelegate(context));

        this.tableType = tableType;
        this.player = player;
        this.context = context;

        this.resultInv = new CraftingResultInventory();
        this.craftInv = new ArtisCraftingInventory(this, tableType.getWidth(), tableType.getHeight());
        if (tableType.hasBlockEntity()) {
            for (int i = 0; i < blockInventory.size(); i++) {
                craftInv.setStack(i, blockInventory.getStack(i));
            }
        }
        ContainerLayout layout = new ContainerLayout(tableType.getWidth(), tableType.getHeight(), tableType.hasCatalystSlot());

        mainPanel = new WPlainPanel();
        setRootPanel(mainPanel);

        this.resultSlot = new WArtisResultSlot(player, craftInv, resultInv, 0, 1, 1, true, syncId);
        int offsetX = 8;
        mainPanel.add(resultSlot, layout.getResultX() + offsetX, layout.getResultY() + 5);

        if (getTableType().hasCatalystSlot()) {
            this.catalystSlot = WItemSlot.of(craftInv, craftInv.size() - 1);
            mainPanel.add(catalystSlot, layout.getCatalystX() + offsetX, layout.getCatalystY() + 1);
    
            WLabel catalystCost = new WLabel("", 0xAA0000).setHorizontalAlignment(HorizontalAlignment.CENTER);
            mainPanel.add(catalystCost, layout.getCatalystX() + offsetX, layout.getCatalystY() + 19);
        }

        this.craftingGrid = WItemSlot.of(craftInv, 0, getTableType().getWidth(), getTableType().getHeight());
        mainPanel.add(craftingGrid, layout.getGridX() + offsetX, layout.getGridY() + 1);

        this.playerInv = this.createPlayerInventoryPanel();
        mainPanel.add(playerInv, layout.getPlayerX() + offsetX, layout.getPlayerY());
    
        WLabel label = new WLabel(tableType.getName(), 0x404040);
        mainPanel.add(label, 8, 6);

        WSprite arrow = new WSprite(new Identifier(Artis.MODID, "textures/gui/translucent_arrow.png"));
        mainPanel.add(arrow, layout.getArrowX() + offsetX, layout.getArrowY() + 5, 22, 15);

        mainPanel.validate(this);
        craftInv.setCheckMatrixChanges(true);
        if (player.world.isClient) {
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            ClientSidePacketRegistry.INSTANCE.sendToServer(Artis.request_sync, buf);
        }
        
        int width = Math.max(176, 74 + tableType.getWidth() * 18);
        mainPanel.setSize(width, 120 + tableType.getHeight() * 18);
    }

    private static BackgroundPainter slotColor(int color) {
        return (matrices, left, top, panel) -> {
            int lo = ScreenDrawing.multiplyColor(color, 0.5F);
            int bg = 0x4C000000;
            int hi = ScreenDrawing.multiplyColor(color, 1.25F);
            if (!(panel instanceof WItemSlot slot)) {
                ScreenDrawing.drawBeveledPanel(matrices, left - 1, top - 1, panel.getWidth() + 2, panel.getHeight() + 2, lo, bg, hi);
            } else {
                for (int x = 0; x < slot.getWidth() / 18; ++x) {
                    for (int y = 0; y < slot.getHeight() / 18; ++y) {
                        if (slot.isBigSlot()) {
                            ScreenDrawing.drawBeveledPanel(matrices, x * 18 + left - 3, y * 18 + top - 3, 24, 24, lo, bg, hi);
                        } else {
                            ScreenDrawing.drawBeveledPanel(matrices, x * 18 + left, y * 18 + top, 18, 18, lo, bg, hi);
                        }
                    }
                }
            }
        };
    }

    public ArtisCraftingInventory getCraftInv() {
        return craftInv;
    }

    public CraftingResultInventory getResultInv() {
        return resultInv;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void addPainters() {
        int color = tableType.getColor();
        if (tableType.hasColor()) {
            mainPanel.setBackgroundPainter(BackgroundPainter.createColorful(color));
            craftingGrid.setBackgroundPainter(slotColor(color));
            if (tableType.hasCatalystSlot())
                catalystSlot.setBackgroundPainter(slotColor(color));
            resultSlot.setBackgroundPainter(slotColor(color));
            playerInv.setBackgroundPainter(slotColor(color));
        } else {
            mainPanel.setBackgroundPainter(BackgroundPainter.VANILLA);
            craftingGrid.setBackgroundPainter(BackgroundPainter.SLOT);
            if (tableType.hasCatalystSlot())
                catalystSlot.setBackgroundPainter(BackgroundPainter.SLOT);
            resultSlot.setBackgroundPainter(BackgroundPainter.SLOT);
            playerInv.setBackgroundPainter(BackgroundPainter.SLOT);
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> {
            if (!tableType.hasBlockEntity()) {
                dropInventory(player, craftInv);
            } else {
                for (int i = 0; i < craftInv.size(); i++) {
                    blockInventory.setStack(i, craftInv.getStack(i));
                }
            }
        });
    }

    @Override
    public int getCraftingWidth() {
        return tableType.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return tableType.getHeight();
    }

    @Override
    public boolean matches(Recipe recipe) {
        return recipe.matches(craftInv, player.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 0;
    }

    @Override
    public int getCraftingSlotCount() {
        return getTableType().getWidth() * getTableType().getHeight();
    }

    // update crafting
    //clientside only
    @Override
    @Environment(EnvType.CLIENT)
    public void updateSlotStacks(int revision, List<ItemStack> stacks, ItemStack cursorStack) {
        craftInv.setCheckMatrixChanges(false);
        super.updateSlotStacks(revision, stacks, cursorStack);
        craftInv.setCheckMatrixChanges(true);
        onContentChanged(null);
    }

    //like vanilla, but not a pile of lag
    public static void updateResult(World world, PlayerEntity player, CraftingInventory inv, CraftingResultInventory result, ArtisTableType artisTableType) {
        if (!world.isClient) {
            ItemStack itemstack = ItemStack.EMPTY;

            Recipe<CraftingInventory> recipe = (Recipe<CraftingInventory>) result.getLastRecipe();
            //find artis recipe first
            if (recipe == null || !recipe.matches(inv, world)) {
                recipe = findArtisRecipe(artisTableType, inv, world);
            }
            //else fall back to vanilla
            if (recipe == null && artisTableType.shouldIncludeNormalRecipes()) {
                recipe = findVanillaRecipe(inv, world);
            }
            
            if (recipe != null) {
                itemstack = recipe.craft(inv);
            }

            result.setStack(0, itemstack);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeIdentifier(recipe != null ? recipe.getId(): Artis.dummy);
            ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, Artis.recipe_sync, buf);
            if(recipe != null) {
                result.setLastRecipe(recipe);
            }
        }
    }

    @Override
    public void onContentChanged(Inventory inv) {
        updateResult(world, player, craftInv, resultInv, tableType);
    }

    @Override
    public ArtisTableType getTableType() {
        return tableType;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int slotIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);
        if (slot.hasStack()) {
            int tableSlotCount = getCraftingSlotCount() + 1 + (tableType.hasCatalystSlot() ? 1 : 0);
            if (slotIndex == getCraftingResultSlotIndex()) {
                return handleShiftCraft(player, this, slot, craftInv, resultInv, tableSlotCount, tableSlotCount + 36);
            }
            ItemStack toTake = slot.getStack();
            stack = toTake.copy();
    
            if (slotIndex < tableSlotCount) {
                if (!this.insertItem(stack, tableSlotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(stack, 0, tableSlotCount, false)) {
                return ItemStack.EMPTY;
            }
    
            if (stack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
    
            if (slotIndex == getCraftingResultSlotIndex()) {
                player.dropItem(toTake, false);
            }
            
            slot.onTakeItem(player, toTake);

        }

        return stack;
    }

    @Override
    public void onSlotClick(int slotNumber, int button, SlotActionType action, PlayerEntity player) {
        if (slotNumber == getCraftingResultSlotIndex() && action == SlotActionType.QUICK_MOVE) {
            transferSlot(player, slotNumber);
        } else {
            super.onSlotClick(slotNumber, button, action, player);
        }
    }

    @Override
    public void populateRecipeMatcher(RecipeMatcher finder) {
        this.craftInv.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.craftInv.clear();
        this.resultInv.clear();
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.resultInv && super.canInsertIntoSlot(stack, slot);
    }

    public static ItemStack handleShiftCraft(PlayerEntity player, ArtisRecipeProvider container, Slot resultSlot, ArtisCraftingInventory input, CraftingResultInventory craftResult, int outStart, int outEnd) {
        ItemStack outputCopy = ItemStack.EMPTY;
        input.setCheckMatrixChanges(false);
        if (resultSlot != null && resultSlot.hasStack()) {

            Recipe<CraftingInventory> recipe = (Recipe<CraftingInventory>) craftResult.getLastRecipe();
            if (recipe == null && container.tableType.shouldIncludeNormalRecipes()) {
                recipe = findVanillaRecipe(input,player.world);
            }
            while (recipe != null && recipe.matches(input, player.world)) {
                ItemStack recipeOutput = resultSlot.getStack().copy();
                outputCopy = recipeOutput.copy();

                recipeOutput.getItem().onCraft(recipeOutput, player.world, player);

                if (!player.world.isClient && !container.insertItem(recipeOutput, outStart, outEnd,true)) {
                    input.setCheckMatrixChanges(true);
                    return ItemStack.EMPTY;
                }

                resultSlot.onQuickTransfer(recipeOutput, outputCopy);
                resultSlot.markDirty();

                if (!player.world.isClient && recipeOutput.getCount() == outputCopy.getCount()) {
                    input.setCheckMatrixChanges(true);
                    return ItemStack.EMPTY;
                }
                
                resultSlot.onTakeItem(player, recipeOutput);
                player.dropItem(recipeOutput, false);
            }
            input.setCheckMatrixChanges(true);
            updateResult(player.world, player, input, craftResult, container.tableType);
        }
        input.setCheckMatrixChanges(true);
        return craftResult.getLastRecipe() == null ? ItemStack.EMPTY : outputCopy;
    }

    public static Recipe<CraftingInventory> findArtisRecipe(ArtisTableType tableType, CraftingInventory inv, World world) {
        return (Recipe<CraftingInventory>) world.getRecipeManager().getFirstMatch(tableType, inv, world).orElse(null);
    }

    public static Recipe<CraftingInventory> findVanillaRecipe(CraftingInventory inv, World world) {
        return world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, inv, world).orElse(null);
    }
}
