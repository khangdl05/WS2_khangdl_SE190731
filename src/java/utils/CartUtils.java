package utils;

import model.Account;
import model.Cart;
import model.CartItem;
import model.dao.CartDAO;
import model.dao.CartItemDAO;

public class CartUtils {
    
    // Fetch the cart from DB for displaying in memory (Cart view mapping)
    public static Cart getCartFromDB(Account account) {
        Cart cart = new Cart();
        if (account == null) {
            return cart;
        }
        
        CartDAO cartDao = new CartDAO();
        CartItemDAO itemDao = new CartItemDAO();
        
        Cart cartDb = cartDao.getCartByAccount(account.getAccount());
        if (cartDb != null) {
            cart.setCartId(cartDb.getCartId());
            cart.setAccount(cartDb.getAccount());
            cart.setCreatedDate(cartDb.getCreatedDate());
            
            for (CartItem itemDb : itemDao.getItemsByCartId(cartDb.getCartId())) {
                cart.getItems().put(itemDb.getProduct().getProductId(), itemDb);
            }
        }
        return cart;
    }
}
