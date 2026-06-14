/*
 * Copyright (c) 2025 Corgi Taco.
 *
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package dev.corgitaco.dataanchor.mixin;

import dev.corgitaco.dataanchor.data.TrackedDataContainer;
import dev.corgitaco.dataanchor.data.registry.TrackedDataKey;
import dev.corgitaco.dataanchor.data.registry.TrackedDataRegistries;
import dev.corgitaco.dataanchor.data.type.chunk.ChunkTrackedData;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

@Mixin(ChunkAccess.class)
public class ChunkAccessMixin implements TrackedDataContainer<ChunkAccess, ChunkTrackedData> {

    @Unique
    private TrackedDataContainer<ChunkAccess, ChunkTrackedData> dataAnchor$trackedDataContainer;

    @Unique
    private final List<BlockEntity> dataAnchor$tickableBlockEntities = new ArrayList<>();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void dataAnchor$onInit(ChunkPos chunkPos, UpgradeData upgradeData, LevelHeightAccessor levelHeightAccessor, Registry biomeRegistry, long inhabitedTime, LevelChunkSection[] sections, BlendingData blendingData, CallbackInfo ci) {
        this.dataAnchor$trackedDataContainer = TrackedDataContainer.create((ChunkAccess) (Object) this, TrackedDataRegistries.CHUNK);
    }

    @Unique
    public List<BlockEntity> dataAnchor$getTickableBlockEntities() {
        return this.dataAnchor$tickableBlockEntities;
    }

    @Override
    public <V> Optional<V> dataAnchor$getTrackedData(TrackedDataKey<V> key) {
        return dataAnchor$trackedDataContainer.dataAnchor$getTrackedData(key);
    }

    @Override
    public <V> void dataAnchor$setTrackedData(TrackedDataKey<V> key, V value) {
        dataAnchor$trackedDataContainer.dataAnchor$setTrackedData(key, value);
    }

    @Override
    public void dataAnchor$createTrackedData() {
        dataAnchor$trackedDataContainer.dataAnchor$createTrackedData();
    }

    @Override
    public Collection<TrackedDataKey<?>> dataAnchor$getTrackedDataKeys() {
        return dataAnchor$trackedDataContainer.dataAnchor$getTrackedDataKeys();
    }
}
