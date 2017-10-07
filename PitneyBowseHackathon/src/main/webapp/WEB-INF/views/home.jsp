<%@page import="com.pb.services.ServiceTax"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Shopping Cart</title>

    <script src="https://code.jquery.com/jquery-2.2.4.js" charset="utf-8"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
   @import url(https://fonts.googleapis.com/css?family=Roboto:300,400,500);

    	* {
    	  box-sizing: border-box;
    	}

    	html,
    	body {
    	  width: 100%;
    	  height: 100%;
    	  padding: 10px;
    	  margin: 0;
    	  background-color: #7EC855;
    	  font-family: 'Roboto', sans-serif;
    	}

    	.shopping-cart {
    	  width: 750px;
    	  height: 423px;
    	  margin: 80px auto;
    	  background: #FFFFFF;
    	  box-shadow: 1px 2px 3px 0px rgba(0,0,0,0.10);
    	  border-radius: 6px;

    	  display: flex;
    	  flex-direction: column;
    	}

    	.title {
    	  height: 60px;
    	  border-bottom: 1px solid #E1E8EE;
    	  padding: 20px 30px;
    	  color: #5E6977;
    	  font-size: 18px;
    	  font-weight: 400;
    	}

    	.item {
    	  padding: 20px 30px;
    	  height: 120px;
    	  display: flex;
    	}

    	.item:nth-child(3) {
    	  border-top:  1px solid #E1E8EE;
    	  border-bottom:  1px solid #E1E8EE;
    	}

    	/* Buttons -  Delete and Like */
    	.buttons {
    	  position: relative;
    	  padding-top: 30px;
    	  margin-right: 60px;
    	}

    	.delete-btn {
    	  display: inline-block;
    	  cursor: pointer;
    	  width: 18px;
    	  height: 17px;
    	  background: url("https://designmodo.com/demo/shopping-cart/delete-icn.svg") no-repeat center;
    	  margin-right: 20px;
    	}

    	.like-btn {
    	  position: absolute;
    	  top: 9px;
    	  left: 15px;
    	  display: inline-block;
    	  background: url('https://designmodo.com/demo/shopping-cart/twitter-heart.png');
    	  width: 60px;
    	  height: 60px;
    	  background-size: 2900%;
    	  background-repeat: no-repeat;
    	  cursor: pointer;
    	}

    	.is-active {
    	  animation-name: animate;
    	  animation-duration: .8s;
    	  animation-iteration-count: 1;
    	  animation-timing-function: steps(28);
    	  animation-fill-mode: forwards;
    	}

    	@keyframes animate {
    	  0%   { background-position: left;  }
    	  50%  { background-position: right; }
    	  100% { background-position: right; }
    	}

    	/* Product Image */
    	.image {
    	  margin-right: 50px;
    	}

    	/* Product Description */
    	.description {
    	  padding-top: 10px;
    	  margin-right: 60px;
    	  width: 115px;
    	}

    	.description span {
    	  display: block;
    	  font-size: 14px;
    	  color: #43484D;
    	  font-weight: 400;
    	}

    	.description span:first-child {
    	  margin-bottom: 5px;
    	}
    	.description span:last-child {
    	  font-weight: 300;
    	  margin-top: 8px;
    	  color: #86939E;
    	}

    	/* Product Quantity */
    	.quantity {
    	  padding-top: 20px;
    	  margin-right: 60px;
    	}
    	.quantity input {
    	  -webkit-appearance: none;
    	  border: none;
    	  text-align: center;
    	  width: 32px;
    	  font-size: 16px;
    	  color: #43484D;
    	  font-weight: 300;
    	}

    	button[class*=btn] {
    	  width: 30px;
    	  height: 30px;
    	  background-color: #E1E8EE;
    	  border-radius: 6px;
    	  border: none;
    	  cursor: pointer;
    	}
    	.minus-btn img {
    	  margin-bottom: 3px;
    	}
    	.plus-btn img {
    	  margin-top: 2px;
    	}
    	button:focus,
    	input:focus {
    	  outline:0;
    	}

    	/* Total Price */
    	.total-price {
    	  width: 83px;
    	  padding-top: 27px;
    	  text-align: center;
    	  font-size: 16px;
    	  color: #43484D;
    	  font-weight: 300;
    	}

    	/* Responsive */
    	@media (max-width: 800px) {
    	  .shopping-cart {
    	    width: 100%;
    	    height: auto;
    	    overflow: hidden;
    	  }
    	  .item {
    	    height: auto;
    	    flex-wrap: wrap;
    	    justify-content: center;
    	  }
    	  .image img {
    	    width: 50%;
    	  }
    	  .image,
    	  .quantity,
    	  .description {
    	    width: 100%;
    	    text-align: center;
    	    margin: 6px 0;
    	  }
    	  .buttons {
    	    margin-right: 20px;
    	  }
    	}

    </style>
    <meta name="robots" content="noindex,follow" />
  </head>
  <body>
    <div class="shopping-cart">
      <!-- Title -->
      <div class="title">
        Shopping Bag
      </div>

      <!-- Product #3 -->
      <div class="item">
        <div class="buttons">
          <span class="delete-btn"></span>
          <span class="like-btn"></span>
        </div>

        <div class="image">
          <img src="https://designmodo.com/demo/shopping-cart/item-3.png" alt="" />
        </div>

        <div class="description">
          <span>Apple iPhone 5s</span>
        </div>

        <div class="quantity">
          <button class="plus-btn" type="button" name="button">
            <img src="https://designmodo.com/demo/shopping-cart/plus.svg" alt="" />
          </button>
          <input type="text" name="name" value="1">
          <button class="minus-btn" type="button" name="button">
            <img src="https://designmodo.com/demo/shopping-cart/minus.svg" alt="" />
          </button>
        </div>

        <div class="total-price">$400</div>
        
      </div>
      <a href="checkOut" class="btn btn-info btn-lg">
          <span class="glyphicon glyphicon-shopping-cart"></span> Check Out
        </a>
    </div>

    <script type="text/javascript">
      $('.minus-btn').on('click', function(e) {
    		e.preventDefault();
    		var $this = $(this);
    		var $input = $this.closest('div').find('input');
    		var value = parseInt($input.val());

    		if (value > 1) {
    			value = value - 1;
    		} else {
    			value = 0;
    		}

        $input.val(value);

    	});

    	$('.plus-btn').on('click', function(e) {
    		e.preventDefault();
    		var $this = $(this);
    		var $input = $this.closest('div').find('input');
    		var value = parseInt($input.val());

    		if (value < 100) {
      		value = value + 1;
    		} else {
    			value =100;
    		}

    		$input.val(value);
    	});

      $('.like-btn').on('click', function() {
        $(this).toggleClass('is-active');
      });
    </script>
  </body>
</html>