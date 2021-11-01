document.addEventListener("DOMContentLoaded", () => {
    const productItems = document.querySelectorAll('.product_item'); // блок каждого товара

// Получаем данные из LocalStorage
    function getCartData() {
        return JSON.parse(localStorage.getItem(user_id.textContent.trim()));
    }

// Записываем данные в LocalStorage
    function setCartData(o) {
        localStorage.setItem(user_id.textContent.trim(), JSON.stringify(o));
    }


// Устанавливаем обработчик события на каждую кнопку "Добавить в корзину"
    productItems.forEach(productItem => {
        productItem.querySelector('.add_item')?.addEventListener('click', (e) => {
            const cartData = getCartData() || {}; // получаем данные корзины или создаём новый объект, если данных еще нет
            const parentBox = e.target.parentNode; // родительский элемент кнопки "Добавить в корзину"
            const itemId = e.target.getAttribute('data-id'); // ID товара
            const itemTitle = parentBox.querySelector('.name').innerHTML; // название товара
            const itemPrice = parentBox.querySelector('.cost').innerHTML; // стоимость товара
            const itemDescription = parentBox.querySelector('.description').innerHTML;
            const itemWeight = parentBox.querySelector('.weight').innerHTML;
            const itemType = e.target.getAttribute('data-type');
            const itemImage = e.target.getAttribute('data-image');
            if (cartData[itemId]) { // если такой товар уже в корзине, то добавляем +1 к его количеству
                cartData[itemId].amount += 1;
            } else { // если товара в корзине еще нет, то добавляем в объект
                cartData[itemId] = {
                    itemTitle,
                    itemPrice: itemPrice.slice(0, -2),
                    itemDescription,
                    itemWeight: itemWeight.slice(0, -2),
                    itemType,
                    itemImage,
                    amount: 1
                };
            }
            setCartData(cartData)
            return false
        });
    })

    function openCart(e) {
        const container = document.querySelector('.cart_main_part')?.querySelector('.left_part')
        if (!container) return
        container.innerHTML = ''
        let summ = 0
        Object.entries(getCartData() || {})?.forEach(([id, product]) => {
            summ += +product.itemPrice * +product.amount
            getItemTemplate(id, product, container)
        })

        return summ
    }

    const getItemTemplate = (id, product, container) => {
        container.innerHTML += `<div class="product_item">
                <div class="first_card _card_item">
                        <div class="img">
                            <img src='data:image/jpg;base64,${product.itemImage}'>
                        </div>
                        <div class="text_part">
                            <div class="name_good">${product.itemTitle}</div>
                            <div class="description">${product.itemDescription}</div>
                            <div class="weight">${product.itemWeight} г</div>
                            <div class="lower_part">
                                <div class="counter">
                                    <div class="counter_title">Количество:</div>
                                    <form class="counter_body">
                                        <div class="input">
                                            <input type="text" value="${product.amount}" id="counter" >
                                        </div>
                                        <div class="arrows">
                                            <div class="arrow _plus" data-id="${id}">
                                                <img src="img/arrow_up.svg" alt="">
                                            </div>
                                            <div class="arrow _minus" data-id="${id}">
                                                <img src="img/arrow_up.svg" alt="">
                                            </div>
                                        </div>
                                    </form>

                                </div>
                                <div class="price" id="price_first_card">${product.itemPrice} p</div>
                            </div>
                        </div>
                        <div class="cross _delete_from_cart" data-id="${id}">
                            <img src="img/cross-grey-16.svg" alt="">
                        </div>
                    </div>
                
                
            </div>`
    }


    /* Очистить корзину */
    document.getElementById('clear_cart')?.addEventListener('click', (e) => {
        localStorage.removeItem(user_id.textContent.trim());
        reRenderCart()
    });


    const changeAmountOfProduct = (id, val) => {
        const data = getCartData()
        const elem = data[id]
        elem.amount += +val
        setCartData(data)
    }


    document.querySelector('#short_btn')?.addEventListener('click', () => {
        if (/^(\s*)?(\+)?([- _():=+]?\d[- _():=+]?){10,14}(\s*)?$/.test(document.querySelector('#cart_number').value)) {
            make_order_input.value = JSON.stringify(Object.entries(getCartData()).map(([id, {amount}]) => ({
                    id,
                    amount
                }))
            )
            total_cost_input.value = openCart()
            document.querySelector('#make_order').submit()
            localStorage.removeItem(user_id.textContent.trim());
        }
    })

    const setTotalPrice = (total_price) => {
        if (!document.querySelector('.total_cart_value')) return
        document.querySelectorAll('.total_cart_value')[0].innerHTML = `${total_price} p`
    }

    function reRenderCart() {
        let summ = openCart()

        setTotalPrice(summ)

        document.querySelectorAll(".arrow._plus")?.forEach((arrow_plus) => {
            arrow_plus.addEventListener('click', function () {
                $(this).parent().siblings('.input').children().val(+$(this).parent().siblings('.input').children().val() + 1)
                const id = $(this).attr('data-id')
                changeAmountOfProduct(id, 1)
                summ += +getCartData()[id].itemPrice
                setTotalPrice(summ)
            })
        })

        document.querySelectorAll(".arrow._minus")?.forEach((arrow_minus) => {
            arrow_minus.addEventListener('click', function () {
                if (+$(this).parent().siblings('.input').children().val() > 0) {
                    $(this).parent().siblings('.input').children().val(+$(this).parent().siblings('.input').children().val() - 1)
                    const id = $(this).attr('data-id')
                    changeAmountOfProduct(id, -1)
                    summ -= +getCartData()[id].itemPrice
                    setTotalPrice(summ)
                }
            })
        })

        document.querySelectorAll('._delete_from_cart')?.forEach(cross => cross.addEventListener('click', () => {
                const data = getCartData()
                delete data[cross.getAttribute('data-id')]
                setCartData(data)
                reRenderCart()

            })
        )
    }

    reRenderCart()

})

