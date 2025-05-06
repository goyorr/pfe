function loadCart() {
  const cart = JSON.parse(localStorage.getItem('cart')) || [];
  const cartItemsDiv = document.getElementById('cart-items');
  const totalPriceEl = document.getElementById('total-price');

  if (cart.length === 0) {
    cartItemsDiv.innerHTML = '<p>Your cart is empty.</p>';
    totalPriceEl.innerHTML = '';
    return;
  }

  let total = 0;
  cartItemsDiv.innerHTML = '';

  cart.forEach((item, index) => {
    const itemDiv = document.createElement('div');
    itemDiv.className = 'cart-item';
    itemDiv.innerHTML = `
      <div class="cart-item-img">
        <img src="${item.img}" alt="${item.name}" class="cart-item-image">
      </div>
      <div class="cart-item-details">
        <p><strong>${item.name}</strong></p>
        <p>Price: MAD ${item.price.toFixed(2)}</p>
        <p>Quantity: ${item.quantity}</p>
        <p>Subtotal: MAD ${(item.price * item.quantity).toFixed(2)}</p>
        <button class="remove-item" data-index="${index}">Remove</button>
      </div>
    `;
    cartItemsDiv.appendChild(itemDiv);
    total += item.price * item.quantity;
  });

  totalPriceEl.textContent = `Total: MAD ${total.toFixed(2)}`;

  const removeButtons = document.querySelectorAll('.remove-item');
  removeButtons.forEach(button => {
    button.addEventListener('click', removeItem);
  });
}

function removeItem(event) {
  const index = event.target.getAttribute('data-index');
  const cart = JSON.parse(localStorage.getItem('cart')) || [];
  
  cart.splice(index, 1);

  localStorage.setItem('cart', JSON.stringify(cart));

  loadCart();
}

document.addEventListener('DOMContentLoaded', loadCart);
