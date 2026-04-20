package com.smartledger

import android.os.Bundle // Το Bundle χρησιμοποιείται στο Android lifecycle και περνάει πληροφορία αποθηκευμένης κατάστασης στη μέθοδο onCreate.
import androidx.activity.ComponentActivity // Η MainActivity κληρονομεί από αυτήν ώστε να λειτουργεί σαν Android activity.
import androidx.activity.compose.setContent // Αντί για XML layouts, εδώ “φορτώνεις” Compose UI.
import com.smartledger.ui.navigation.SmartLedgerNavHost // navigation host της εφαρμογής, ορίζεται όλο το flow των οθονών
import com.smartledger.ui.theme.SmartLedgerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Η activity φορτώνει μόνο το theme και το κεντρικό navigation host
        setContent {
            SmartLedgerTheme {
                SmartLedgerNavHost()
            }
        }
    }
}
