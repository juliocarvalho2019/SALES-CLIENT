package br.com.carvalho.salesclient.product

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.carvalho.salesclient.network.SalesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "ProductListViewModel"
private var viewModelJob = Job()
private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


class ProductListViewModel : ViewModel() {

    init {
        getProducts()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getProducts() {
        Log.i(TAG, "Preparing to request products list")
        coroutineScope.launch {
            val getProductsDeferred = SalesApi.retrofitService.getProducts()
            Log.i(TAG, "Loading products")
            val productsList = getProductsDeferred.await()
            Log.i(TAG, "Number of products ${productsList.size}")
        }
        Log.i(TAG, "Products list requested")
    }


}