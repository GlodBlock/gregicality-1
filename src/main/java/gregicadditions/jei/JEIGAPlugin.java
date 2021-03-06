package gregicadditions.jei;

import gregicadditions.Gregicality;
import gregicadditions.machines.multi.impl.HotCoolantRecipeLogic;
import gregicadditions.recipes.nuclear.GTHotCoolantRecipeWrapper;
import gregicadditions.recipes.nuclear.HotCoolantRecipeMap;
import gregicadditions.recipes.nuclear.HotCoolantRecipeMapCategory;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.common.items.MetaItems;
import gregtech.common.metatileentities.MetaTileEntities;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class JEIGAPlugin implements IModPlugin {

    private IIngredientBlacklist itemBlacklist;
    private IIngredientRegistry iItemRegistry;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new GAMultiblockInfoCategory(registry.getJeiHelpers()));

        for (HotCoolantRecipeMap hotCoolantRecipeMap : HotCoolantRecipeMap.getRecipeMaps()) {
            registry.addRecipeCategories(new HotCoolantRecipeMapCategory(hotCoolantRecipeMap, registry.getJeiHelpers().getGuiHelper()));
        }
    }


    @Override
    public void register(IModRegistry registry) {
        itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
        iItemRegistry = registry.getIngredientRegistry();
        GAMultiblockInfoCategory.registerRecipes(registry);
        registry.getJeiHelpers().getIngredientBlacklist().addIngredientToBlacklist(MetaItems.DATA_CONTROL_CIRCUIT_IV.getStackForm());

        for (HotCoolantRecipeMap hotCoolantRecipeMap : HotCoolantRecipeMap.getRecipeMaps()) {
            List<GTHotCoolantRecipeWrapper> recipeList = hotCoolantRecipeMap.getRecipeList().stream()
                    .map(GTHotCoolantRecipeWrapper::new)
                    .collect(Collectors.toList());
            registry.addRecipes(recipeList, Gregicality.MODID + ":" + hotCoolantRecipeMap.unlocalizedName);
        }

        for (ResourceLocation metaTileEntityId : GregTechAPI.META_TILE_ENTITY_REGISTRY.getKeys()) {
            MetaTileEntity metaTileEntity = GregTechAPI.META_TILE_ENTITY_REGISTRY.getObject(metaTileEntityId);
            //noinspection ConstantConditions
            if (metaTileEntity.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null) != null) {
                IControllable workableCapability = metaTileEntity.getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null);
                if (workableCapability instanceof HotCoolantRecipeLogic) {
                    HotCoolantRecipeMap recipeMap = ((HotCoolantRecipeLogic) workableCapability).recipeMap;
                    registry.addRecipeCatalyst(metaTileEntity.getStackForm(), Gregicality.MODID + ":" + recipeMap.unlocalizedName);
                }
            }
        }
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IRecipeRegistry recipeRegistry = jeiRuntime.getRecipeRegistry();
        IRecipeCategory recipeCategory = recipeRegistry.getRecipeCategory("gregtech:multiblock_info");

        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.ELECTRIC_BLAST_FURNACE.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.ELECTRIC_BLAST_FURNACE.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.DIESEL_ENGINE.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.CRACKER.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.DISTILLATION_TOWER.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.LARGE_GAS_TURBINE.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.LARGE_PLASMA_TURBINE.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.LARGE_STEAM_TURBINE.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.MULTI_FURNACE.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.IMPLOSION_COMPRESSOR.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.VACUUM_FREEZER.getStackForm());
        itemBlacklist.addIngredientToBlacklist(MetaTileEntities.PYROLYSE_OVEN.getStackForm());


        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.ELECTRIC_BLAST_FURNACE.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.DIESEL_ENGINE.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.CRACKER.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.DISTILLATION_TOWER.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.LARGE_GAS_TURBINE.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.LARGE_PLASMA_TURBINE.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.LARGE_STEAM_TURBINE.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.MULTI_FURNACE.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.IMPLOSION_COMPRESSOR.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.VACUUM_FREEZER.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));
        recipeRegistry.getRecipeWrappers(recipeCategory, recipeRegistry.createFocus(IFocus.Mode.OUTPUT, MetaTileEntities.PYROLYSE_OVEN.getStackForm())).forEach(iRecipeWrapper -> recipeRegistry.hideRecipe((IRecipeWrapper) iRecipeWrapper, "gregtech:multiblock_info"));

    }
}
