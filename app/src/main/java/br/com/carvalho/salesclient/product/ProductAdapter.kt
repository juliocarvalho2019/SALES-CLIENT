package br.com.carvalho.salesclient.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.carvalho.salesclient.network.Product
import br.com.carvalho.salesclient.databinding.ItemProductBinding

class ProductAdapter(val onProductClickListener: ProductClickListener) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiff) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        //criar uma instância de ProductViewHolder inflando um layout de um item da lista de produtos
        return ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
        // evento de clique no item da lista
        holder.itemView.setOnClickListener {
            onProductClickListener.onClick(product)
        }
    }

    class ProductViewHolder(private var binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    companion object ProductDiff : DiffUtil.ItemCallback<Product>() {
        //chamada pelo RecyclerView para verificar se duas instâncias de Product representam o mesmo produto
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        //chamada para verificar se o conteúdo a ser exibido de duas instâncias são os mesmos.
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return ((oldItem.id == newItem.id)
                    && (oldItem.name.equals(newItem.name))
                    && (oldItem.code.equals(newItem.code))
                    && (oldItem.price == newItem.price))
        }
    }

    class ProductClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }

}
