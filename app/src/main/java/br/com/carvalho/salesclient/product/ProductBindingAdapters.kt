package br.com.carvalho.salesclient.product

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.carvalho.salesclient.network.Product

@BindingAdapter("productsList")
fun bindProductsList(recyclerView: RecyclerView, products: List<Product>?) {
    products?.let {
        val adapter = recyclerView.adapter as ProductAdapter
        adapter.submitList(products)
    }
}

@BindingAdapter("productPrice")
fun bindProductPrice(txtProductPrice: TextView, productPrice: Double?) {
    txtProductPrice.text = productPrice?.let {"$ " + "%.2f".format(it)}
}