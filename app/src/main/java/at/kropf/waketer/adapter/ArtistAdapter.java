package at.kropf.waketer.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

import at.kropf.waketer.R;
import at.kropf.waketer.customviews.TickPlusDrawable;
import at.kropf.waketer.model.Artist;

public class ArtistAdapter extends ArrayAdapter<Artist> {

    Context context;

    public ArtistAdapter(Context context, List<Artist> objects) {
        super(context, R.layout.list_item_artist, objects);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_artist, null);

            // Set up the ViewHolder.
            holder = new ViewHolder();
            holder.artistImage = (ImageView) convertView.findViewById(R.id.artistImage);
            holder.artistName = (TextView) convertView.findViewById(R.id.artistName);
            holder.checkArtist = convertView.findViewById(R.id.checkView);


            // Store the holder with the view.
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UrlImageViewHelper.setUrlDrawable(holder.artistImage, getItem(position).getImageBySize("extralarge").getImageName(), R.drawable.ic_launcher);

        holder.artistName.setText(getItem(position).getName()+"");

        final TickPlusDrawable tickPlusDrawable = new TickPlusDrawable(8, context.getResources().getColor(android.R.color.black), Color.WHITE);

        holder.checkArtist.setBackground(tickPlusDrawable);

        return convertView;
    }

    private class ViewHolder {
        ImageView artistImage;
        TextView artistName;
        View checkArtist;
    }
}