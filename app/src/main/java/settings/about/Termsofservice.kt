package settings.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.minor1.R

class termsofservice : Fragment() {
   lateinit var description:TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View=inflater.inflate(R.layout.fragment_termsofservice, container, false)
        // Inflate the layout for this fragment
        description=view.findViewById(R.id.descrpiptionterms)
        description.text=" User Agreement:\n" +
                "   - Users agree to the terms when accessing the hall booking app.\n" +
                "\n" +
                " User Responsibilities:\n" +
                "   - Users are responsible for providing accurate event details and adhering to the policies of the booked hall.\n"+"\n"+
                " Booking Process:\n" +
                "   - Users follow a simple process: select a hall or marriage garden, see venue and event details, make a secure payment, and receive a confirmation " +
                "and chat for more bargaining.\n" +
                "\n" +
                " Payment Terms:\n" +
                "   - Payment methods include debit cards , UPI and online transfers. Pricing is transparent, and additional fees are clearly communicated.\n" +
                "\n" +
                " Cancellation and Refund Policy:\n" +
                "   - Users can cancel within a specified timeframe for a full or partial refund, depending on the notice period.\n" +
                "\n" +
                " Code of Conduct:\n" +
                "   - Users must conduct events respectfully, follow hall guidelines, and face consequences for any violations.\n" +
                "\n" +
                " Intellectual Property:\n" +
                "   - The app owns the technology, while users retain ownership of their event content uploaded to the platform.\n" +
                "\n"+
                " Termination of Services:\n" +
                "   - The app may terminate services for users violating terms, ensuring fair use and protecting the app's integrity.\n" +
                "\n" +
                " Liability and Disclaimers:\n" +
                "   - The app limits liability, especially for events beyond its control, and includes disclaimers to clarify its role as a booking platform.\n" +
                "\n" +
                " Jurisdiction and Governing Law:\n" +
                "    - Legal disputes fall under the jurisdiction of a specified court, following the laws of a particular region.\n"





        return view
    }

}