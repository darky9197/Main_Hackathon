import React from "react";
import ReactDOM from "react-dom/client";
import {
  createBrowserRouter,
  RouterProvider,
  useOutletContext,
} from "react-router-dom";
import App from "./App";
import "./index.css";

import Login from "./pages/Login";
import Register from "./pages/Register";
import RestaurantList from "./pages/RestaurantList";
import Menu from "./pages/Menu";
import Cart from "./pages/Cart";
import Orders from "./pages/Orders";

const LoginWrapper = () => <Login onLogin={useOutletContext().handleLogin} />;
const RestaurantListWrapper = () => (
  <RestaurantList token={useOutletContext().token} />
);
const MenuWrapper = () => {
  const { token, addToCart } = useOutletContext();
  return <Menu token={token} addToCart={addToCart} />;
};
const CartWrapper = () => {
  const { cart, removeFromCart, token, clearCart } = useOutletContext();
  return (
    <Cart
      cart={cart}
      removeFromCart={removeFromCart}
      token={token}
      clearCart={clearCart}
    />
  );
};
const OrdersWrapper = () => {
  const { token, userId } = useOutletContext();
  return <Orders token={token} userId={userId} />;
};

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        index: true,
        element: <RestaurantListWrapper />,
      },
      {
        path: "login",
        element: <LoginWrapper />,
      },
      {
        path: "register",
        element: <Register />,
      },
      {
        path: "restaurant/:id",
        element: <MenuWrapper />,
      },
      {
        path: "cart",
        element: <CartWrapper />,
      },
      {
        path: "orders",
        element: <OrdersWrapper />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<RouterProvider router={router} />);
