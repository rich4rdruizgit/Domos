package proyecto.com.domos.ui.fragments.shop;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

import proyecto.com.domos.data.DB;
import proyecto.com.domos.data.dao.ItemShopDao;
import proyecto.com.domos.data.model.ItemShop;

/**
 * Created by aranda on 17/02/18.
 */

public class ShopViewModel extends ViewModel
{
    private ItemShopDao dao;

    public ShopViewModel()
    {
        super();
        dao = DB.con.itemShopDao();
    }

    public LiveData<List<ItemShop>>getAllItemShop()
    {
        return dao.allItemsShop();
    }

    public void deleteItem(ItemShop itemShop) {
        new ActionAsyncTask(dao,1).execute(itemShop);
    }

    public void insertItem(ItemShop itemShop) {
        new ActionAsyncTask(dao,0).execute(itemShop);
    }

    private static class ActionAsyncTask extends AsyncTask<ItemShop, Void, Void> {

        private ItemShopDao dao;
        int type;//0=insert 1= delete

        ActionAsyncTask(ItemShopDao itemShopDao,int type) {
            dao = itemShopDao;
            this.type=type;
        }

        @Override
        protected Void doInBackground(final ItemShop... params) {

            switch (type)
            {
                case 0://insert
                    dao.insert(params[0]);
                    break;
                case 1://delete
                    dao.delete(params[0]);
                    break;
            }
            return null;
        }

    }
}
