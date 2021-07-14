package com.photon.xome.feature.home.view

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.transform.RoundedCornersTransformation
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.photon.xome.R
import com.photon.xome.feature.home.viewmodel.HomeViewModel
import com.photon.xome.theme.shapes

@ExperimentalFoundationApi
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.size(50.dp))
            XomeTitle()
            SearchTextField(homeViewModel)
            PhotosTitle()

            Box(modifier = Modifier.fillMaxSize()) {
                PhotosListEmptyView()
                PhotosList(homeViewModel)
            }
        }
    }
}

@Composable
fun XomeTitle() {
    Text(
        text = stringResource(id = R.string.app_name),
        textAlign = TextAlign.Start,
        style = TextStyle(fontWeight = FontWeight.Bold,fontSize = 30.sp),
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .paddingFromBaseline(40.dp, 0.dp)
            .padding(16.dp, 16.dp)
    )
}


@Composable
fun SearchTextField(homeViewModel: HomeViewModel) {
    val currentContext = LocalContext.current

    var searchKeyword by rememberSaveable {
        mutableStateOf("")
    }

    val errorOccured by rememberSaveable {
        mutableStateOf(false)
    }

    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        value = searchKeyword,
        onValueChange = {
            searchKeyword = it
        },
        isError = errorOccured,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_photos),
                color = MaterialTheme.colors.onPrimary,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = null
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                if(searchKeyword.isNotEmpty()){
                    homeViewModel.getFlickrImages(searchKeyword)
                }
                else{
                    Toast.makeText(currentContext, "Enter valid search keyword", Toast.LENGTH_SHORT).show()
                }
                localFocusManager.clearFocus()
            }
        ),
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            //.height(56.dp)
            .padding(16.dp, 0.dp, 16.dp, 0.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        ),
        textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.background,
            textColor = MaterialTheme.colors.onPrimary,
            focusedIndicatorColor = MaterialTheme.colors.onPrimary,
            focusedLabelColor = MaterialTheme.colors.onPrimary,
            cursorColor = MaterialTheme.colors.onPrimary
        )
    )
}

@Composable
fun PhotosTitle() {
    Text(
        text = stringResource(id = R.string.photos_title),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.h1,
        color = MaterialTheme.colors.onPrimary,
        modifier = Modifier
            .paddingFromBaseline(40.dp, 0.dp)
            .padding(16.dp, 16.dp)
    )
}

@ExperimentalFoundationApi
@Composable
fun PhotosList(homeViewModel: HomeViewModel) {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    Log.d("grid", "PhotosList: screenWidthDp ${screenWidthDp}")
    val gridSize = screenWidthDp / 3
    Log.d("grid", "PhotosList: gridSize ${gridSize}")

    val photosList by homeViewModel.photosListState.collectAsState()
    if(photosList.isNotEmpty()){
        photosList.let {
            LazyVerticalGrid(
                cells = GridCells.Adaptive(gridSize.dp)
            ) {
                items(
                    items = it
                ) { photo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(gridSize.dp),
                        elevation = 2.dp,
                        backgroundColor = MaterialTheme.colors.surface,
                        shape = MaterialTheme.shapes.large,
                    ){
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top,
                        ) {
                            val photoURL = "http://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
                            Log.d("PhotosList", "photoURL: $photoURL")
                            val painter = rememberCoilPainter(
                                photoURL,
                                fadeIn = true,
                                requestBuilder = {
                                    transformations(
                                        RoundedCornersTransformation(
                                            14F,
                                            14F,
                                            14F,
                                            14F
                                        )
                                    )
                                }
                            )
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            )
                            when (painter.loadState) {
                                is ImageLoadState.Loading -> {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_image_search),
                                        contentDescription = null
                                    )
                                }
                                is ImageLoadState.Error -> {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_image_search),
                                        contentDescription = null
                                    )
                                }
                                else -> {

                                }
                            }
                        }
                    }

                }
            }
        }
    }
    else{
        //PhotosListEmptyView(homeViewModel = homeViewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PhotosListEmptyView() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.photos_list_empty_message),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold,color = Color.LightGray),
                modifier = Modifier.wrapContentSize().padding(100.dp,0.dp)
            )
        }
}



/*@Preview(
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    widthDp = 360, heightDp = 640,
    name = "BasePreview"
)*/

/*@Composable
fun PreviewLightHomeScreen() {
    BloomTheme(false){
        HomeScreen(homeViewModel)
    }
}*/

/*
@ExperimentalMaterialApi
@Preview(
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_TYPE_NORMAL,
    widthDp = 360, heightDp = 640,
    name = "BasePreview"
)
@Composable
fun PreviewDarkHomeScreen() {
    BloomTheme(true) {
        HomeScreen(arrayListOf(
            GardenTheme(1, stringResource(R.string.desert_chic),"https://www.pexels.com/photo/assorted-color-flowers-2132227/")
        ), arrayListOf(
            Plant(1, stringResource(R.string.monstera),"",stringResource(R.string.plant_description))
        ))
    }
}*/
