package com.newangle.healthy.pages.home.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newangle.healthy.di.fragment.FragmentComponent
import javax.inject.Inject

class HomeOperationViewModel @Inject constructor(application: Application, fragmentComponent: FragmentComponent) : AndroidViewModel(application) {

    private val _frequency = MutableLiveData(DEFAULT_FREQUENCY)
    val frequency: LiveData<Int> get() = _frequency
    private val _energy = MutableLiveData(DEFAULT_ENERGY)
    val energy: LiveData<Int> get() = _energy
    private val _pulse = MutableLiveData(DEFAULT_PULSE)
    val pulse: LiveData<Int> get() = _pulse
    private val _gender = MutableLiveData<Gender>(Gender.FEMALE)
    val gender: LiveData<Gender> get() = _gender
    private val _launchState = MutableLiveData(false)
    val launchState get() = _launchState

    init {
        fragmentComponent.inject(this)
    }

    fun increaseFrequency() = _frequency.safeUpdate(max = MAX_FREQUENCY) { it + 1 }

    fun decreaseFrequency() = _frequency.safeUpdate(min = MIN_FREQUENCY) { it - 1 }

    fun increaseEnergy() = _energy.safeUpdate(max = MAX_ENERGY) { it + 1 }

    fun decreaseEnergy() = _energy.safeUpdate(min = MIN_ENERGY) { it - 1 }

    fun increasePulse() = _pulse.safeUpdate(max = MAX_PULSE) { it + 1 }

    fun decreasePulse() = _pulse.safeUpdate(min = MIN_PULSE) { it - 1 }

    fun changeGender() {
        _gender.value = if (_gender.value == Gender.MALE) Gender.FEMALE else Gender.MALE
    }

    fun changeLaunchState() {
        _launchState.value.let {
            _launchState.value = !it
        }
    }

    private fun MutableLiveData<Int>.safeUpdate(max: Int = Integer.MAX_VALUE, min: Int = Integer.MIN_VALUE, update: (Int) -> Int ) {
        value?.let { current ->
            val newValue = update(current)
            if (newValue in min..max) value = newValue
        }
    }

    companion object {
        private const val MAX_FREQUENCY = 10
        private const val MIN_FREQUENCY = 1
        private const val DEFAULT_FREQUENCY =5
        private const val MAX_ENERGY = 80
        private const val MIN_ENERGY = 1
        private const val DEFAULT_ENERGY = 30
        private const val MAX_PULSE = 100
        private const val MIN_PULSE = 1
        private const val DEFAULT_PULSE = 60
    }

    sealed interface Gender {
        object MALE : Gender
        object FEMALE : Gender
    }
}