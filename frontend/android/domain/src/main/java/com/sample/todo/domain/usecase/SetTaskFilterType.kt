package com.sample.todo.domain.usecase

import com.sample.todo.domain.model.TaskFilterType
import com.sample.todo.domain.repository.PreferenceRepository
import javax.inject.Inject

// TODO suspect race condition, try to do in transaction?
class SetTaskFilterType @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    suspend operator fun invoke(newFilter: TaskFilterType) {
        val oldOrdinal = preferenceRepository.getTaskFilterTypeOrdinal()
        val oldFilter: TaskFilterType? = TaskFilterType.parse(oldOrdinal)
        if (oldFilter == newFilter)
            return
        preferenceRepository.setTaskFilterTypeOrdinal(newFilter.ordinal)
    }
}
