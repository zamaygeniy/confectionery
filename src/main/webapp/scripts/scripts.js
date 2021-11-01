jQuery(function () {

    $('.burger').click(function () {



        if ($(this).hasClass('_filter')) {
            $('.filter_part').removeClass('_active');
            $('.burger').removeClass('_filter');
            $('body').removeClass('_lock');
        } else {
            $(this).toggleClass('_active');
            $('.menu').toggleClass('_active');
            $('body').toggleClass('_lock');
            $('.burger_content').toggleClass('_active');
            $('.black_href').toggleClass('_active');
            $('.black_href').toggleClass('_active_2');
        }
    });

    $('.filter_btn').click(function () {
        $('.filter_part').addClass('_active');
        $('.burger').addClass('_filter');
        $('body').addClass('_lock');
        $('html, body').animate({
            scrollTop: 0
        }, 300);
    });

    $('.black_href').click(function () {
        $('.burger_content').removeClass('_active');
        $('.black_href').removeClass('_active');
        $('.black_href').removeClass('_active_2');
        $('body').removeClass('_lock');
        $('.video').removeClass('_active');

    })
    $('input').on('focus', function () {
        $('.search').addClass('_active');

    })
    $('input').on('blur', function () {
        $('.search').removeClass('_active');
    });

    $('.choose_item').click(function () {
        $(this).siblings().children('.icon').removeClass('_active');
        $(this).children('.icon').addClass('_active');
    })

    $('.color').click(function () {
        $('.color').removeClass('_active');
        $(this).addClass('_active');
    });
    $('.filter_op').click(function () {
        $(this).toggleClass('_active');
    });
    $('.catalog_item>.heart').click(function () {

        $(this).toggleClass('_active');
    })
    $('.male').click(function () {
        $(this).siblings().removeClass('_active');
        $(this).addClass('_active');
    })
    $('.filter_header').click(function () {
        $('.filter_header').not(this).siblings().slideUp();
        $('.filter_header').not(this).removeClass('_active');
        $(this).siblings().slideToggle();
        $(this).toggleClass('_active');
    })


    function drop_down() {
        $('.filter_drop_down_title').click(function () {
            $(this).siblings('.drop_down_body').slideToggle();
            $(this).toggleClass('_active');
        })
        $('.check_item').click(function () {
            $(this).toggleClass('_active');
        })
    }
    drop_down();
    function tabs() {

        $(".tab_text").not(":first").hide();
        $(".tab_link").click(function () {
            $(".tab_link").removeClass("_active").eq($(this).index()).addClass("_active");
            $(".tab_text").hide().eq($(this).index()).fadeIn()
        }).eq(0).addClass("_active");
    }
    tabs();


    $('.menu_item._dropped').mouseenter(function () {
        $(this).children('.menu_body').addClass('_active');
        $(this).addClass('_active');
        $('.black_href').addClass('_active');
    });
    $('.menu_item._dropped').mouseleave(function () {
        $(this).children('.menu_body').removeClass('_active');
        $(this).removeClass('_active');
        $('.black_href').removeClass('_active');
    })

    $('#step_1').click(function () {
        $(this).parent().slideUp();
        $('.do_bet').slideDown();
        $('.color_cont').slideUp();
        $('.choose_size').slideUp();
    });
    $('#step_3').click(function () {

        $('.do_bet>form').addClass('_error');
        $('.error_messege').slideDown();
        $('.error').slideDown();
    });
    $('#step_2').click(function () {
        $(this).parent().slideUp();
        $('.good_broned').slideDown();
    });
    $('#step_4').click(function () {
        $('.good_broned').slideUp();
        $('.good_delivered').slideDown();
    });
    $('#step_5').click(function () {
        $('.good_broned').slideUp();
        $('.canceling_ggod').slideDown();
    })
    $('.good_th_item').click(function () {
        $(this).siblings().removeClass('_active');
        $(this).addClass('_active');
    });
    $('.card_item').not('.first_card').click(function () {
        $(this).siblings().removeClass('_active');
        $(this).addClass('_active');
    });
    $('#step_6').click(function () {
        $('.do_bet').slideUp();
        $('.bet_win').slideDown();
    })
    $('.drop_title').click(function () {
        $('.drop_title').not(this).siblings().slideUp();
        $(this).siblings().slideToggle();
    });
    $('.drop_link').click(function () {
        $(this).parent().slideUp();
        $(this).parent().parent().addClass('_active');
        $(this).parent().siblings().children('.text').text($(this).text());
    })

    $('.watch_video_btn').click(function () {
        $('.video').addClass('_active');
        $('.black_href').addClass('_active_2');
    })

    $('.cross_video').click(function () {
        $('.video').removeClass('_active');
        $('body').removeClass('_lock');
        $('.black_href').removeClass('_active_2');
    })


    $('#clode_cart').click(function () {
        $('.cart_content').removeClass('_active');
        $('body').removeClass('_lock');

    })
    var color_item = $('.color.radio').children();
    color_item.on('click', function () {
        color_item.removeClass('_active');
        $(this).toggleClass('_active');
    });
    var color_item_ = $('.color_item.radio');
    color_item_.on('click', function () {
        color_item_.removeClass('_active');
        $(this).toggleClass('_active');
    });
    function item_slider() {
        var item_slider = $('.slider_big_body');
        item_slider.slick({
            slidesToShow: 1,
            slidesToScroll: 1,

            infinite: false,
            fade: true,
            centerMode: true,
            nextArrow: $('.slider_arrow_next'),
            prevArrow: $('.slider_arrow_prev'),
            responsive: [
                {
                    breakpoint: 650,
                    settings: {
                        variableWidth: true,
                        fade: false,
                        centerMode: false,
                    },
                },
            ]

        });
        $('.slider_small_body>.slider_item').click(function () {
            var number = $(this).index();

            item_slider.slick('slickGoTo', number);
        });


    }
    if ($('main').hasClass('_item')) {
        item_slider();
    }

    function scroll() {
        jQuery('#scroll').scrollbar();
        jQuery('#scroll_2').scrollbar();
    }
    scroll();
    $(window).scroll(function () {
        if ($('header').hasClass('_main')) {
            if ($(this).scrollTop() > 20) {
                $('.header').removeClass('_transparent');
                $('#button_small').removeClass('_transparent')

                $('.logo>a>img').attr('src', "img/logo.svg");
            } else {
                $('.header').addClass('_transparent')
                $('.header').addClass('_fixed');
                $('.logo>a>img').attr('src', "img/logo.svg");
                $('#button_small').addClass('_transparent');
            }
        }
    })

    $('._resevies_slider').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        infinite: false,
        dots: true,
        appendDots: $('._resevies_controls'),
        //nextArrow: $('.slider_arrow_next'),
        //prevArrow: $('.slider_arrow_prev'),

    });
    $('.check_box_item').click(function () {

        if ($(this).attr('_type') != undefined) {
            $(this).toggleClass('_active');
        }
    })

    let playce_holder = () => {
        $('.input>input').on('focus', function () {

            if ($(this).attr('_type') != undefined) {
                $(this).val('');
            }
        })
        $('.input>input').on('blur', function () {
            if ($(this).val().length == 0) {
                if ($(this).attr('_type') == 1) {
                    $(this).val('Поиск')
                }
            }
        })
    }
    playce_holder();
    function slider() {
        $('.tab_link').click(function () {
            $('.good_item>.img_slider').children('.slider_body').slick('setPosition');
        })
        for (var i = 0; i < $('.good_item>.img_slider').length; i++) {
            $('.good_item>.img_slider').eq(i).children('.slider_body').slick({
                slidesToShow: 1,
                slidesToScroll: 1,

                infinite: false,
                prevArrow: $('.good_item>.img_slider').eq(i).children('.slider_controls').children('.slider_arrow').eq(0),
                nextArrow: $('.good_item>.img_slider').eq(i).children('.slider_controls').children('.slider_arrow').eq(1),


            });

        }
        $('.back_slider').slick({
            slidesToShow: 1,
            slidesToScroll: 1,
            arrows: false,
            autoplay: true,
            autoplaySpeed: 2000,
        })
    }

    function popups() {
        $('#order_made').click(function () {
            $('.order_made_popup').addClass('_active');
            $('body').addClass('_lock');
            $('.black_href_2').addClass('_active');
        });
        $('#cancel_order').click(function () {
            $('.cancel_order_popup').addClass('_active');
            $('body').addClass('_lock');
            $('.black_href_2').addClass('_active');
        })
        $('#add_to_cart').click(function () {
            $('.add_to_cart_popup').addClass('_active');
            $('body').addClass('_lock');
            $('.black_href_2').addClass('_active');
        })
        $('.black_href_2').click(function () {
            $('._popup').removeClass('_active');
            $('body').removeClass('_lock');
            $('.black_href_2').removeClass('_active');
        });



        $('._popup>.content_box>.popup_btn_cont>.btn_1').click(function () {
            $('._popup').removeClass('_active');
            $('body').removeClass('_lock');
            $('.black_href_2').removeClass('_active');
        });
        $('._popup>.content_box>.popup_btn_cont>.btn_2').click(function () {
            $('._popup').removeClass('_active');
            $('body').removeClass('_lock');
            $('.black_href_2').removeClass('_active');
        });
        $('._popup>.content_box>.popup_btn').click(function () {
            $('._popup').removeClass('_active');
            $('body').removeClass('_lock');
            $('.black_href_2').removeClass('_active');
        });

    }
    function change_back() {
        $('._choose_color').click(function () {
            $('.h1_block').addClass('_active');
            $(this).parent().parent().addClass('_active');
            $('.tabs_header').addClass('_active');
            $('.changing_header').addClass('_active');

        });
        $('.change_back>._drop_title').click(function () {
            $(this).siblings().toggleClass('_active');
        });
        $(document).mouseup(function (e) { // событие клика по веб-документу
            var div = $(".change_back>._drop_title"); // тут указываем ID элемента
            if (!div.is(e.target) // если клик был не по нашему блоку
                && div.has(e.target).length === 0) { // и не по его дочерним элементам
                $(".change_back>._drop_title").siblings().removeClass('_active'); // скрываем его
            }
        });
        var current_color = $('.page_title_cont').css('background-color');
        $('.color_item').click(function () {
            $('.color_item').not(this).removeClass('_active');
            $(this).addClass('_active');
            var color = $(this).css('background-color');

            $('.page_title_cont').css({
                'background-color': color,
            })



            function rgb2hex(r, g, b) {
                return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
            }
            function splicing_components(color) {
                color = color.toString();
                var result = [];

                result.push(color.substring(4, color.indexOf(',')));
                result.push(color.substring(color.indexOf(',') + 2, color.lastIndexOf(',')));
                result.push(color.substring(color.lastIndexOf(',') + 2, color.lastIndexOf(')')));
                return result;
            }
            splicing_components(color);
            $('.result').text(rgb2hex(+splicing_components(color)[0], +splicing_components(color)[1], +splicing_components(color)[2]).toUpperCase());
        });

        $('.submit_btn._anti_green_hover').click(function () {
            $('.h1_block').removeClass('_active');
            $('.change_back').removeClass('_active');
            $('.change_back').children('.drop_body').removeClass('_active');
            $('.tabs_header').removeClass('_active');
            $('.changing_header').removeClass('_active');
        })
        $('.cancel_change._white_btn').click(function () {
            $('.h1_block').removeClass('_active');
            $('.change_back').removeClass('_active');
            $('.change_back').children('.drop_body').removeClass('_active');
            $('.tabs_header').removeClass('_active');
            $('.changing_header').removeClass('_active');
            $('.page_title_cont').css({
                'background-color': current_color,
            })
        })

    }
    let UI_kit = () => {
        $('.radio_button').click(function () {
            $(this).siblings().removeClass('_active');
            $(this).addClass('_active')
        });
    }
    let Cart = () => {

        $('.cart_item>.text>.cart_item_footer>.cross').click(function () {

            $(this).parent().parent().parent().outerHeight($(this).parent().parent().parent().outerHeight());
            var cur_elem = $(this).parent().parent().parent()
            $(this).parent().parent().parent().empty();

            cur_elem.append($('<div class="upper_del">Товар был удален из корзины.</div>'));
            cur_elem.append($('<div class= "lower_del" >Отменить</div>'));
            cur_elem.addClass('_active');
            if ($('#scroll_2').children('.cart_item').not('._active').length == 0) {
                $('#cart_popup_btn').addClass('_active');
            }
        });
        if ($('#scroll_2').children('.cart_item').length != 0) {
            $('#cart_icon').parent().addClass('_exsist');

        }
        $('#cart_icon').click(function () {
            $(this).siblings('.cart_content').toggleClass('_active');
            if ($('body').width() < 600) {
                $('body').toggleClass('_lock');
            }
        });
        $(document).mouseup(function (e) { // событие клика по веб-документу
            var div = $(".cart_content"); // тут указываем ID элемента
            if (!div.is(e.target) // если клик был не по нашему блоку
                && div.has(e.target).length === 0) { // и не по его дочерним элементам
                $('.cart_content').removeClass('_active'); // скрываем его
            }
        });
    }
    let Cart_drop_down = () => {
        $('._card_item').not('.first_card').children('.card_header').click(function () {
            if ($('html').width() < 600) {
                $(this).siblings().slideToggle();
                $(this).toggleClass('_active')
            }
        })
        $('.cart_container>h1').click(function () {
            if ($('html').width() < 600) {
                $('.first_card').slideToggle();
                $(this).toggleClass('_active')
            }
        })
    }
    let inputs = () => {
        $('input').change(function () {
            if ($(this).val() != 0) {
                $(this).addClass('_filled');
            } else {
                $(this).removeClass('_filled');
            }
        })
        $('input').on('focus', function () {
            $(this).parent().addClass('_focus');
        })
        $('input').on('blur', function () {
            $(this).parent().removeClass('_focus');
        })
        $('.input_cross').click(function () {
            $(this).siblings('input').removeClass('_filled');
            $(this).siblings('input').val('');
        })
    }

    inputs();
    Cart_drop_down();
    UI_kit();
    change_back();
    slider();
    popups();
    Cart();
});


