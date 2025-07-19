package com.newangle.healthy.pages.home.home

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.DrawableUtils
import androidx.appcompat.widget.ThemeUtils
import androidx.compose.ui.res.stringResource
import com.newangle.healthy.NewAngleApp
import com.newangle.healthy.R
import com.newangle.healthy.base.BaseFragment
import com.newangle.healthy.base.logger.LogUtils
import com.newangle.healthy.databinding.HomeOperationFragmentLayoutBinding
import com.newangle.healthy.di.fragment.FragmentComponent
import javax.inject.Inject

class HomeOperationFragment : BaseFragment() {

    @Inject
    lateinit var viewModel: HomeOperationViewModel
    private var _binding: HomeOperationFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private val fragmentComponent: FragmentComponent by lazy {
        ((requireActivity().application) as NewAngleApp).appComponent.fragmentComponent().create()
    }

    private val onClickListener = View.OnClickListener {
        LogUtils.i("click button")
        when (it.id) {
            binding.frequencyValueUpButton.id -> {
                LogUtils.i("increase frequency ${viewModel.frequency.value}")
                viewModel.increaseFrequency()
            }
            binding.frequencyValueDownButton.id -> {
                LogUtils.i("decrease frequency ${viewModel.frequency.value}")
                viewModel.decreaseFrequency()
            }
            binding.energyValueUpButton.id -> {
                LogUtils.i("increase energy ${viewModel.energy.value}")
                viewModel.increaseEnergy()
            }
            binding.energyValueDownButton.id -> {
                LogUtils.i("decrease energy ${viewModel.energy.value}")
                viewModel.decreaseEnergy()
            }
            binding.pulseValueUpButton.id -> {
                LogUtils.i("increase pulse ${viewModel.pulse.value}")
                viewModel.increasePulse()
            }
            binding.pulseValueDownButton.id -> {
                LogUtils.i("decrease pulse ${viewModel.pulse.value}")
                viewModel.decreasePulse()
            }
            binding.selectGenderIcon.id -> {
                viewModel.changeGender()
            }
            binding.startButtonLayout.id -> {
                viewModel.changeLaunchState()
            }
        }
    }

    override fun onAttach(context: Context) {
        fragmentComponent.inject(this)
        super.onAttach(context)
        LogUtils.i("weixiaolong---- home operation fragment on attach ${System.currentTimeMillis()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeOperationFragmentLayoutBinding.inflate(inflater, container, false)
        LogUtils.i("weixiaolong---- home operation fragment on create view  ${System.currentTimeMillis()}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding) {
            frequencyValueUpButton.setOnClickListener(onClickListener)
            frequencyValueDownButton.setOnClickListener(onClickListener)
            energyValueUpButton.setOnClickListener(onClickListener)
            energyValueDownButton.setOnClickListener(onClickListener)
            pulseValueUpButton.setOnClickListener(onClickListener)
            pulseValueDownButton.setOnClickListener(onClickListener)
            selectGenderIcon.setOnClickListener(onClickListener)
            startButtonLayout.setOnClickListener(onClickListener)
        }
    }

    private fun setupViewModel() {
        viewModel.frequency.observe(viewLifecycleOwner, ::updateFrequency)
        viewModel.energy.observe(viewLifecycleOwner, ::updateEnergy)
        viewModel.pulse.observe(viewLifecycleOwner, ::updatePulse)
        viewModel.gender.observe(viewLifecycleOwner, ::updateGender)
        viewModel.launchState.observe(viewLifecycleOwner, ::updateLaunchState)
    }

    private fun updateFrequency(value : Int) {
        binding.frequencyValue.text = value.toString()
    }

    private fun updateEnergy(value : Int) {
        binding.energyValue.text = value.toString()
    }

    private fun updatePulse(value : Int) {
        binding.pulseValue.text = value.toString()
    }

    private fun updateGender(gender : HomeOperationViewModel.Gender) {
        when (gender) {
            HomeOperationViewModel.Gender.MALE -> {
                binding.selectedGenderFigure.setImageResource(R.drawable.gender_male_figure)
            }
            HomeOperationViewModel.Gender.FEMALE -> {
                binding.selectedGenderFigure.setImageResource(R.drawable.gender_female_figure)
            }
        }
    }

    private fun updateLaunchState(value : Boolean) {
        binding.startButton.text = if (value) getString(R.string.start_work_state_stop) else getString(R.string.start_work_state_start)
            val endDrawable =
                if (value)
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.pause_state)
                else
                    AppCompatResources.getDrawable(requireActivity(), R.drawable.start_state)
        binding.startButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, endDrawable, null)
    }

    override fun onResume() {
        super.onResume()
        LogUtils.i("weixiaolong---- home operation fragment on resume ${System.currentTimeMillis()}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HomeOperationFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}