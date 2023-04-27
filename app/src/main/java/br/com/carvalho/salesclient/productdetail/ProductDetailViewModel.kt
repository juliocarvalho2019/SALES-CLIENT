package br.com.carvalho.salesclient.productdetail

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

private const val TAG = "ProductDetailViewModel"

class ProductDetailViewModel(private val code: String) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product>
        get() = _product

    init {
        getProduct()
    }

    private fun getProduct() {
        Log.i(TAG, "Preparing to request a product by its code")
        coroutineScope.launch {
            val getProductDeferred = SalesApi.retrofitService.getProductByCode(code)
            Log.i(TAG, "Fetching product by its code")
            val productByCode = getProductDeferred.await()
            Log.i(TAG, "Name of the product ${productByCode.name}")
            _product.value = productByCode
        }
        Log.i(TAG, "Product requested by code")
    }

    override fun onCleared() {
        Log.i(TAG, "onCleared")
        super.onCleared()
        viewModelJob.cancel()
    }
}
