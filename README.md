# MVVMLight
A toolkit help to build Android MVVM Application，We have more
attributes for Data Binding  of View(like Uri for ImageView) ,we create some command for deal with event( like click of Button),also have a global message pipe to communicate with other ViewModel.
 ##Data Binding##
Binding URI to the ImageView with bind:uri will make it loading bitmap from URI and render to ImageView automatically.

       <ImageView
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:layout_alignParentRight="true"
       bind:uri="@{viewModel.imageUrl}"
       bind:placeholderImageRes="@{R.drawable.ic_launcher}"/>

AdapterView like ListView、RecyclerView 、ViewPager is convenient, bind it to the collection view with app:items and app:itemView,You should use an ObservableList to automatically update your view based on list changes.

      <android.support.v7.widget.RecyclerView
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         bind:itemView="@{viewModel.itemView}"
         bind:items="@{viewModel.itemViewModel}"
         bind:layoutManager="@{LayoutManagers.linear()}"

 Other attributes supported:
  - *ImageView*
        <attr name="uri" />
        <!--width for ResizeOptions (use Fresco to load bitmap). -->
        <attr name="request_width" format="integer" />
        <!--height for ResizeOptions (use Fresco to load bitmap). -->
        <attr name="request_height" format="integer" />
        <attr name="placeholderImageRes" format="reference|color" />

  - *ListView*、*ViewPager*、*RecyclerView*
         <!-- require ItemView  or  ItemViewSelector   -->
         <attr name="itemView" />
         <!-- require List<ViewModel> bind to ItemView to presentation.-->
         <attr name="items" />
         <!-- require a adapter which type of BindingRecyclerViewAdapter<T> to AdapterView-->
         <attr name="adapter" />
         <attr name="dropDownItemView" format="reference" />
         <attr name="itemIds" format="reference" />
         <attr name="itemIsEnabled" format="reference" />
         <!-- require PageTitles<T>-->
         <attr name="pageTitles" format="reference" />

  - *ViewGroup*
         <!-- require ItemView  or ItemViewSelector -->
         <attr name="itemView" />
         <!-- require List<ViewModel> bind to ItemView to presentation.-->
         <attr name="viewModels" format="reference" />

  - *EditText*
         <!-- require boolean value to decide whether requestFocus for view. -->
         <attr name="requestFocus"  format="boolean" />

  - *SimpleDraweeView*
         <!-- require String to load Image"-->
         <attr name="uri" />

  - *WebView*
         <!-- require String render to html show in webview-->
         <attr name="render" format="string" />

##Command Binding##
When RecyclerView scroll to end of list,we have onLoadMoreCommand to deal with event.

     <android.support.v7.widget.RecyclerView
           android:id="@+id/recyclerView"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           bind:onLoadMoreCommand="@{viewModel.loadMoreCommand}"/>

 In ViewModel define a ReplyCommand<Integer> field to deal with this event.

       public final ReplyCommand<Integer> loadMoreCommand = new ReplyCommand<>(
        (count) -> {
            /*count: count of list items*/
             int page=count / LIMIT;
             loadData(page)
        });
More command binding is supported:
 - *View*
        <!-- require ReplyCommand to deal with view click event. -->
        <attr name="clickCommand" format="reference" />
        <!-- require ReplyCommand<Boolean> to deal with view focus change event.
        ReplyCommand would has params which means if view hasFocus.-->
        <attr name="onFocusChangeCommand" format="reference" />
        <!-- require ReplyCommand<MotionEvent> -->
        <attr name="onTouchCommand" />

 - *ListView*、*RecyclerView*
        <!-- require ReplyCommand<Integer> -->
        <attr name="onScrollStateChangedCommand" />
        <!-- require ReplyCommand<ListViewScrollDataWrapper> -->
        <attr name="onScrollChangeCommand" />
        <!-- require ReplyCommand<Integer> count of list items-->
        <attr name="onLoadMoreCommand" format="reference" />

 - *ViewPager*
         <!--require ReplyCommand<ViewPagerDataWrapper> -->
        <attr name="onPageScrolledCommand" format="reference" />
        <!--require ReplyCommand<Integer> -->
        <attr name="onPageSelectedCommand" format="reference" />
        <!--require ReplyCommand<Integer> -->
        <attr name="onPageScrollStateChangedCommand" format="reference" />

 - *EditText*
        <!--require ReplyCommand<TextChangeDataWrapper> -->
        <attr name="beforeTextChangedCommand" format="reference" />
        <!--require ReplyCommand<TextChangeDataWrapper> -->
        <attr name="onTextChangedCommand" format="reference" />
        <!--require ReplyCommand<String> -->
        <attr name="afterTextChangedCommand" format="reference" />

 - *ImageView*
         <!--  require ReplyCommand<Bitmap> -->
         <attr name="onSuccessCommand" format="reference" />
         <!--require ReplyCommand<CloseableReference<CloseableImage>> -->
         <attr name="onFailureCommand" format="reference" />

 - *ScrollView*、*NestedScrollView*
        <!-- require ReplyCommand<ScrollDataWrapper> -->
        <attr name="onScrollChangeCommand" />
        <!-- require ReplyCommand<NestScrollDataWrapper> -->
        <attr name="onScrollChangeCommand" />

 - *SwipeRefreshLayout*
        <!-- require RelayCommand<> -->
        <attr name="onRefreshCommand" format="reference" />

###Messenger###

- global message broadcast without deliver data

        /* TOKEN: like Action of broadcast with who register this token will be notified when event occur.*/
        Messenger.Default().sendNoMsg(TOKEN);
         /* context: it usually to be a activity ，this parameter is represent to
                     a receiver which is mean for convenient when unregister message.
            TOKEN: like Action of broadcast with who register this token will be notified when event occur.
            (data)->{ }:Action to deal with event. */
        Messenger.Default().register(context, TOKEN, () -> { });
- global message broadcast (carry data to receiver)

       Messenger.getDefault().send(data, TOKEN)
       /*  context:
         * TOKEN:
         * Data.class: type of deliver data.
         * (data)->{ }: function to deal with event which has data is deliver by sender.*/
       Messenger.getDefault().register(context, TOKEN, Data.class, (data) -> { });
- send to specify target (inactive)

      Messenger.getDefault().sendToTarget(T message, R target)
      Messenger.getDefault().sendNoMsgToTargetWithToken(Object token,R target)
      Messenger.getDefault().sendNoMsgToTarget(Object target)
- cancel register
      /* Usually  call in OnDestroy() function*/
       Messenger.getDefault().unregister(Object recipient)"

