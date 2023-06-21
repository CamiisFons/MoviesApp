package com.example.moviesapp.ui.moviedetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.common.extensions.toast
import com.example.moviesapp.common.util.Constants
import com.example.moviesapp.databinding.FragmentMovieDetailBinding
import com.example.moviesapp.model.MovieDetails
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailBinding
    private val args: MovieDetailFragmentArgs by navArgs()
    private lateinit var movie: MovieDetails
    private val viewModel: MovieDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = args.movie
        setHasOptionsMenu(true)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = movie.title
        setupView()

    }

    private fun setupView() = with(binding) {
        Glide.with(this@MovieDetailFragment)
            .load(Constants.posterBaseUrl + movie.posterPath)
            .into(imgMovie)
        tvNameMovie.text = movie.title
        tvDescriptionMovie.text = movie.overview

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                viewModel.insert(movie)
                toast(getString(R.string.saved_successfully))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}