package com.misoramen.todoapp.view

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.misoramen.todoapp.R
import com.misoramen.todoapp.databinding.FragmentCreateTodoBinding
import com.misoramen.todoapp.model.Todo
import com.misoramen.todoapp.util.NotificationHelper
import com.misoramen.todoapp.util.TodoWorker
import com.misoramen.todoapp.viewmodel.DetailTodoViewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(), RadioClick, TodoEditClick, DateClickListener, TimeClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding:FragmentCreateTodoBinding
    private lateinit var viewModel:DetailTodoViewModel
    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),NotificationHelper.REQUEST_NOTIF)
        }

        binding.todo = Todo("","",3,0, 0)
        binding.radioListener = this
        binding.addListener = this
        binding.listenerDate = this
        binding.listenerTime = this

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
//        binding.btnAdd.setOnClickListener {
//            val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                .setInitialDelay(30, TimeUnit.SECONDS)
//                .setInputData(
//                    workDataOf(
//                        "title" to "Todo created",
//                        "message" to "Stay focus"
//                    )
//                )
//                .build()
//            WorkManager.getInstance(requireContext()).enqueue(workRequest)
//
//            val radio = view.findViewById<RadioButton>(binding.radioGroupPriority.checkedRadioButtonId)
//            var todo = Todo(
//                binding.txtTitle.toString(),
//                binding.txtNotes.toString(),
//                radio.tag.toString().toInt(),
//                0
//            )
//            val list = listOf(todo)
//            viewModel.addTodo(list)
//            val notif = NotificationHelper(view.context)
//            notif.createNotification("Todo Created", "A new todo has been created! Stay focus!")
//            Toast.makeText(view.context, "Data added", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,
                                            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==NotificationHelper.REQUEST_NOTIF) {
            if(grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                NotificationHelper(requireContext())
                    .createNotification("Todo Created",
                        "A new todo has been created! Stay focus!")
            }
        }
    }

    override fun onTodoEditClick(v: View) {
        val c = Calendar.getInstance()
        c.set(year, month, day, hour, minute, 0)
        val today = Calendar.getInstance()
        val diff = (c.timeInMillis/1000L)-(today.timeInMillis/1000L)
        binding.todo!!.todo_date = (c.timeInMillis/1000L).toInt()

        val list = listOf(binding.todo!!)
        viewModel.addTodo(list)
        Toast.makeText(view?.context, "Data added", Toast.LENGTH_SHORT).show()

        val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
            .setInitialDelay(diff, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
                    "title" to "Todo created",
                    "message" to "Stay focus"
                )
            )
            .build()
        WorkManager.getInstance(requireContext()).enqueue(workRequest)
        Navigation.findNavController(v).popBackStack()
    }

    override fun onRadioClick(v: View) {
        binding.todo!!.priority = v.tag.toString().toInt()
    }

    override fun onDateClick(v: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        activity?.let {it1 -> DatePickerDialog(it1, this, year, month, day).show()}
    }

    override fun onTimeClick(v: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Calendar.getInstance().let {
            it.set(year, month, day)
            binding.txtDate.setText(day.toString().padStart(2, '0') + "-" + month.toString().padStart(2, '0') + "-" + year)
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        binding.txtTime.setText(
            hourOfDay.toString().padStart(2,'0') + ":" +
                    minute.toString().padStart(2,'0')
        )
        this.hour = hourOfDay
        this.minute = minute
    }


}