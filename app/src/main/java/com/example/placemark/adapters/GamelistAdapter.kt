import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.example.placemark.R
import com.example.placemark.models.GameModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_game.*
import kotlinx.android.synthetic.main.card_game.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.concurrent.Executor


class GameListAdapter constructor(
    private var games: List<GameModel>,
    private val listener: GameListener
) : RecyclerView.Adapter<GameListAdapter.MainHolder>(), AnkoLogger {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_game,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val game = games[holder.adapterPosition]
        holder.bind(game, listener)
    }

    override fun getItemCount(): Int = games.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger{
        fun bind(game: GameModel, listener: GameListener) {
            itemView.gameTitle.text = game.name
            itemView.notes.text = game.notes
            itemView.codeText.text = game.code
            itemView.storeLink.text = game.url

            val banner = "https://cdn.cloudflare.steamstatic.com/steam/apps/${game.appid}/header_alt_assets_3.jpg"
//            val bitmap: Bitmap = Picasso.get().load(banner).get()
            Picasso.get().load(banner).into(itemView.image)
            info { "Debug: Setting image to ${itemView.image}" }

            itemView.setOnClickListener {
                TransitionManager.beginDelayedTransition(it.gameCardView, AutoTransition())
                if(it.expandableCard.visibility == View.GONE)
                    it.expandableCard.visibility = View.VISIBLE
                else
                    it.expandableCard.visibility = View.GONE

                val banner = "https://cdn.cloudflare.steamstatic.com/steam/apps/${game.appid}/header_alt_assets_3.jpg"
                Picasso.get().load(banner).into(itemView.image)
                info { "Debug: Setting image to ${itemView.image.width}" }
            }

//            itemView.setOnClickListener { listener.onGameClick(game) }

            itemView.codeText.setOnClickListener {
                var clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var clip = ClipData.newPlainText("label", game.code)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(it.context, "CODE COPIED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

interface GameListener {
    fun onGameClick(game: GameModel)
}