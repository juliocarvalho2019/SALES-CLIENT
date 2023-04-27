package br.com.carvalho.salesclient.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.carvalho.salesclient.network.Product
import br.com.carvalho.salesclient.network.SalesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "ProductListViewModel"

class ProductListViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

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
            //Isso fará com que quem estiver observando o atributo products seja notificado da alteração
            _products.value = productsList

        }
        Log.i(TAG, "Products list requested")
    }

    fun refreshProducts() {
        getProducts()
    }


}