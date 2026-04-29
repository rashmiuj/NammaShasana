package com.example.nammashasane.ui.screens


import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import android.preference.PreferenceManager
import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.delay
import com.example.nammashasana.data.AppStorage
import com.example.nammashasana.data.Inscription
import com.example.nammashasana.data.PreservationReport

val Beige = Color(0xFFFDF5E6)
val BeigeDark = Color(0xFFF5DEB3)
val BrownAccent = Color(0xFF8B4513)
val BrownDark = Color(0xFF3E2723)
val StoneGrey = Color(0xFF795548)
val StoneLight = Color(0xFFD7CCC8)
val Parchment = Color(0xFFF1E4D3)
val ParchmentDarkText = Color(0xFF5A3E26)

@Composable
fun NammaShasanaApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("map") { MapScreen(navController) }
        composable("storylist") { StoryListScreen(navController) }
        composable("details/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            val context = LocalContext.current
            val inscription = AppStorage.getInscriptions(context).find { it.id == id }
            if (inscription != null) {
                DetailsScreen(navController, inscription)
            }
        }
        composable("phototag") { PhotoTagScreen(navController) }
        composable("phototag?id={id}") { backStackEntry ->
            PhotoTagScreen(navController, backStackEntry.arguments?.getString("id"))
        }
        composable("report") { ReportScreen(navController) }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BrownAccent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(BrownDark, RoundedCornerShape(100.dp))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("ಶಾಸನ", color = Beige, fontSize = 32.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Namma-Shasane",
            color = Beige,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "\"Turning Old Stones into Talking History\"",
            color = BeigeDark,
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ನಮ್ಮ Shasane", color = Beige) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownAccent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                HomeCard(
                    title = "Find a Shasane",
                    icon = Icons.Outlined.Place,
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("map") }

                HomeCard(
                    title = "Photo Tag",
                    icon = Icons.Outlined.AddCircle,
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("phototag") }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                HomeCard(
                    title = "Story View",
                    icon = Icons.Outlined.MenuBook,
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("storylist") }

                HomeCard(
                    title = "Preservation Alert",
                    icon = Icons.Outlined.Warning,
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("report") }
            }
        }
    }
}

@Composable
fun HomeCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = title, tint = BrownAccent, modifier = Modifier.size(48.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, color = BrownDark, textAlign = TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    var inscriptions by remember { mutableStateOf<List<Inscription>>(emptyList()) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context.packageName
        inscriptions = AppStorage.getInscriptions(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Heritage Trail", color = Beige) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Beige)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownAccent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            AndroidView(
                factory = { ctx ->
                    MapView(ctx).apply {
                        setTileSource(TileSourceFactory.MAPNIK)
                        setMultiTouchControls(true)
                        controller.setZoom(6.5)
                        controller.setCenter(GeoPoint(15.3173, 75.7139))
                    }
                },
                update = { mapView ->
                    mapView.overlays.clear()
                    inscriptions.forEach { inscription ->
                        try {
                            val parts = inscription.gpsCoordinates.split(",")
                            if (parts.size == 2) {
                                val lat = parts[0].trim().toDouble()
                                val lon = parts[1].trim().toDouble()

                                val marker = Marker(mapView)
                                marker.position = GeoPoint(lat, lon)
                                marker.title = inscription.name
                                marker.snippet = "Tap to view"
                                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                marker.setOnMarkerClickListener { m, _ ->
                                    m.showInfoWindow()
                                    navController.navigate("details/${inscription.id}")
                                    true
                                }
                                mapView.overlays.add(marker)
                            }
                        } catch (e: Exception) {
                            // Ignore parsing errors
                        }
                    }
                    mapView.invalidate()
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryListScreen(navController: NavController) {
    val context = LocalContext.current
    var inscriptions by remember { mutableStateOf<List<Inscription>>(emptyList()) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(navBackStackEntry) {
        inscriptions = AppStorage.getInscriptions(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Story View", color = Beige) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Beige)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownAccent)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Read the histories of our ancient inscriptions.", color = StoneGrey, fontWeight = FontWeight.Bold)
            }
            items(inscriptions) { inscription ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("details/${inscription.id}") },
                    colors = CardDefaults.cardColors(containerColor = Parchment),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(inscription.name, fontWeight = FontWeight.Bold, color = BrownDark, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(inscription.description, color = Color.DarkGray, maxLines = 3)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Read details →", color = BrownAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, inscription: Inscription) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(inscription.name, color = Beige) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Beige)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("phototag?id=${inscription.id}") }) {
                        Icon(Icons.Outlined.Edit, contentDescription = "Edit", tint = Beige)
                    }
                    IconButton(onClick = {
                        AppStorage.deleteInscription(context, inscription.id)
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Delete", tint = Beige)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownAccent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(StoneLight),
                contentAlignment = Alignment.Center
            ) {
                val resId = inscription.imageResName?.let {
                    context.resources.getIdentifier(it, "drawable", context.packageName)
                } ?: 0

                if (inscription.imageBitmap != null) {
                    Image(
                        bitmap = inscription.imageBitmap.asImageBitmap(),
                        contentDescription = "Inscription Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (resId != 0) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = resId),
                        contentDescription = "Inscription Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    AsyncImage(
                        model = inscription.imageUrl.ifBlank { "https://placehold.co/600x400/8B4513/FDF5E6?text=Image+Not+Found" },
                        contentDescription = "Inscription Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(inscription.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BrownDark)
                Text(inscription.location, color = StoneGrey)
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = Parchment),
                    shape = RoundedCornerShape(4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Modern Kannada Translation", fontSize = 12.sp, color = ParchmentDarkText, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(inscription.kannadaTranslation, color = BrownDark, fontSize = 18.sp, lineHeight = 24.sp)

                        Spacer(modifier = Modifier.height(12.dp))
                        Divider(color = BeigeDark)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text("Record Type", fontSize = 12.sp, color = ParchmentDarkText, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(inscription.giftOrLaw, color = BrownAccent, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Meaning & Context", fontWeight = FontWeight.Bold, color = BrownDark)
                Spacer(modifier = Modifier.height(4.dp))
                Text(inscription.description, color = Color.DarkGray, lineHeight = 20.sp)
            }
        }
    }
}

fun getCoordinatesForLocation(loc: String): String {
    val norm = loc.lowercase()
    if (norm.contains("belagavi") || norm.contains("belgaum")) return "15.8497, 74.4977"
    if (norm.contains("bengaluru") || norm.contains("bangalore")) return "12.9716, 77.5946"
    if (norm.contains("mysuru") || norm.contains("mysore")) return "12.2958, 76.6394"
    if (norm.contains("davangere")) return "14.4644, 75.9218"
    if (norm.contains("hubballi") || norm.contains("hubli") || norm.contains("dharwad")) return "15.3647, 75.1240"
    if (norm.contains("mangaluru") || norm.contains("mangalore")) return "12.9141, 74.8560"
    if (norm.contains("raichur")) return "16.2076, 77.3463"
    if (norm.contains("badami")) return "15.9189, 75.6761"
    if (norm.contains("hampi") || norm.contains("hosapete")) return "15.3350, 76.4600"
    if (norm.contains("shivamogga") || norm.contains("shimoga")) return "13.9299, 75.5681"
    if (norm.contains("hassan")) return "13.0033, 76.1004"
    if (norm.contains("ballari") || norm.contains("bellary")) return "15.1394, 76.9214"
    if (norm.contains("kalaburagi") || norm.contains("gulbarga")) return "17.3297, 76.8343"
    if (norm.contains("vijayapura") || norm.contains("bijapur")) return "16.8302, 75.7100"
    if (norm.contains("chikkamagaluru")) return "13.3161, 75.7720"
    if (norm.contains("udupi")) return "13.3409, 74.7421"
    return "${15.0 + Math.random() * 2 - 1}, ${76.0 + Math.random() * 2 - 1}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoTagScreen(navController: NavController, editId: String? = null) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var modernKannada by remember { mutableStateOf("") }
    var recordType by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf("") }
    var capturedImage by remember { mutableStateOf<android.graphics.Bitmap?>(null) }

    LaunchedEffect(editId) {
        if (editId != null) {
            val old = AppStorage.getInscriptions(context).find { it.id == editId }
            if (old != null) {
                name = old.name
                location = old.location
                desc = old.description
                modernKannada = old.kannadaTranslation
                recordType = old.giftOrLaw
                photoUrl = old.imageUrl
                capturedImage = old.imageBitmap
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImage = bitmap
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Photo Tag", color = Beige) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Beige)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownAccent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            if (capturedImage != null) {
                Image(
                    bitmap = capturedImage!!.asImageBitmap(),
                    contentDescription = "New Tag Image",
                    modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            } else if (photoUrl.isNotBlank()) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "New Tag Image",
                    modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = photoUrl,
                    onValueChange = { photoUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { cameraLauncher.launch(null) },
                    colors = ButtonDefaults.buttonColors(containerColor = BrownAccent),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Camera", color = Beige)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Inscription Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = modernKannada, onValueChange = { modernKannada = it }, label = { Text("Modern Kannada Translation") }, modifier = Modifier.fillMaxWidth(), minLines = 2)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = recordType, onValueChange = { recordType = it }, label = { Text("Record Type") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth(), minLines = 3)

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val finalUrl = if (photoUrl.isBlank()) "https://placehold.co/600x400/8B4513/FDF5E6?text=New+Inscription" else photoUrl
                    if (editId != null) {
                        val original = AppStorage.getInscriptions(context).find { it.id == editId }
                        if (original != null) {
                            AppStorage.updateInscription(context, original.copy(
                                name = name.takeIf { it.isNotBlank() } ?: original.name,
                                location = location.takeIf { it.isNotBlank() } ?: original.location,
                                description = desc,
                                kannadaTranslation = modernKannada.takeIf { it.isNotBlank() } ?: original.kannadaTranslation,
                                giftOrLaw = recordType,
                                imageUrl = finalUrl,
                                imageBitmap = if (photoUrl.isBlank()) capturedImage else null
                            ))
                        }
                        Toast.makeText(context, "Inscription Updated Successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        AppStorage.addInscription(
                            context,
                            Inscription(
                                id = System.currentTimeMillis().toString(),
                                name = name.takeIf { it.isNotBlank() } ?: "Unknown Inscription",
                                location = location.takeIf { it.isNotBlank() } ?: "Unknown",
                                dynasty = "Unknown",
                                king = "Unknown",
                                description = desc,
                                kannadaTranslation = modernKannada.takeIf { it.isNotBlank() } ?: "ಅನುವಾದದ ಅಗತ್ಯವಿದೆ",
                                giftOrLaw = recordType,
                                gpsCoordinates = getCoordinatesForLocation(location),
                                imageUrl = finalUrl,
                                imageBitmap = if (photoUrl.isBlank()) capturedImage else null
                            )
                        )
                        Toast.makeText(context, "Inscription Tagged Successfully!", Toast.LENGTH_SHORT).show()
                    }
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrownAccent)
            ) {
                Text("Save & Add to Map")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(navController: NavController) {
    val context = LocalContext.current
    var place by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }

    var reports by remember { mutableStateOf<List<PreservationReport>>(emptyList()) }

    LaunchedEffect(Unit) {
        reports = AppStorage.getReports(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Preservation Alert", color = Beige) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Beige)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BrownAccent)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Text("Report damaged inscriptions so authorities can be notified.", color = Color.Red, modifier = Modifier.padding(bottom = 16.dp))

                OutlinedTextField(value = place, onValueChange = { place = it }, label = { Text("Place Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("Damage Type") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (place.isNotBlank()) {
                            AppStorage.addReport(context, PreservationReport(System.currentTimeMillis().toString(), place, type, desc))
                            place = ""
                            type = ""
                            desc = ""
                            reports = AppStorage.getReports(context)
                            Toast.makeText(context, "Report Submitted", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter a place name", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrownAccent)
                ) {
                    Text("Submit Report")
                }

                Spacer(modifier = Modifier.height(32.dp))

                if (reports.isNotEmpty()) {
                    Text("Previous Reports", fontWeight = FontWeight.Bold, color = BrownDark, fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
                    for (rep in reports) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                            colors = CardDefaults.cardColors(containerColor = Parchment)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(rep.placeName, fontWeight = FontWeight.Bold, color = BrownDark, fontSize = 18.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Damage: ${rep.damageType}", color = Color.Red, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(rep.description, color = StoneGrey, fontSize = 14.sp)
                            }
                        }
                    }
                } else {
                    Text("No reports submitted yet.", color = StoneGrey, fontStyle = FontStyle.Italic)
                }
            }
        }
    }
}