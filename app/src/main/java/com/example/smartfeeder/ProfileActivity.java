package com.example.smartfeeder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import java.util.Calendar;

import android.os.Build;
import android.content.pm.PackageManager;

import android.widget.Toast;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1234;
    private Spinner animalTypeSpinner;
    private Spinner breedSpinner;
    private Spinner spinnerGender;
    private Spinner spinnerBloodType;
    private ArrayAdapter<CharSequence> dogBreedAdapter;
    private ArrayAdapter<CharSequence> catBreedAdapter;
    private ImageView imageViewProfile;
    private EditText editTextMemo, editTextName, editTextGender, editTextWeight, editTextBloodType, editTextRegistrationNumber, editTextBirthDate;;
    private SharedPreferences sharedPreferences;
    private Uri selectedImage;

    private static final int REQUEST_STORAGE_PERMISSION = 5678;

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            }
        }
    }

    // 권한 요청 및 이미지 선택
    private void requestStoragePermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestStoragePermission();
        } else {
            pickImageFromGallery();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // 변수 초기화
        animalTypeSpinner = findViewById(R.id.animalTypeSpinner);
        breedSpinner = findViewById(R.id.breedSpinner);
        editTextMemo = findViewById(R.id.editTextMemo);
        imageViewProfile = findViewById(R.id.imageViewProfile);
        editTextName = findViewById(R.id.editTextName);
        spinnerGender = findViewById(R.id.spinnerGender);
        editTextWeight = findViewById(R.id.editTextWeight);
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        editTextRegistrationNumber = findViewById(R.id.editTextRegistrationNumber);
        editTextBirthDate = findViewById(R.id.editTextBirthDate);

        // 생년월일 선택
        editTextBirthDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ProfileActivity.this,
                    (view, selectedYear, monthOfYear, dayOfMonth) -> editTextBirthDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + selectedYear),
                    year, month, day
            );
            datePickerDialog.show();
        });


        // 동물 유형에 대한 어댑터 설정
        ArrayAdapter<CharSequence> animalTypeAdapter = ArrayAdapter.createFromResource(this, R.array.animal_type_array, android.R.layout.simple_spinner_item);
        animalTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        animalTypeSpinner.setAdapter(animalTypeAdapter);

        // 성별 유형에 대한 어댑터 설정
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // 혈액형 유형에 대한 어댑터 설정
        ArrayAdapter<CharSequence> bloodTypeAdapter = ArrayAdapter.createFromResource(this, R.array.blood_type_array, android.R.layout.simple_spinner_item);
        bloodTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodType.setAdapter(bloodTypeAdapter);




        // 프로필 사진 클릭 시 갤러리에서 사진 선택
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermissionAndPickImage();
            }
        });


        // 프로필 정보 저장
        sharedPreferences = getSharedPreferences("ProfileData", MODE_PRIVATE);

        dogBreedAdapter = ArrayAdapter.createFromResource(this, R.array.dog_breeds_array, android.R.layout.simple_spinner_item);
        dogBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        catBreedAdapter = ArrayAdapter.createFromResource(this, R.array.cat_breeds_array, android.R.layout.simple_spinner_item);
        catBreedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 동물 종류 선택 시 해당하는 종류의 품종을 보여줌
        animalTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Dog")) {
                    breedSpinner.setAdapter(dogBreedAdapter);
                } else if (parent.getItemAtPosition(position).equals("Cat")) {
                    breedSpinner.setAdapter(catBreedAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 저장된 프로필 정보 불러오기
        String savedAnimalType = sharedPreferences.getString("animalType", "Dog");
        String savedBreed = sharedPreferences.getString("breed", "");
        String savedMemo = sharedPreferences.getString("memo", "");
        String savedName = sharedPreferences.getString("name", "");
        String savedGender = sharedPreferences.getString("gender", "");
        String savedWeight = sharedPreferences.getString("weight", "");
        String savedBloodType = sharedPreferences.getString("bloodType", "");
        String savedRegistrationNumber = sharedPreferences.getString("registrationNumber", "");
        String savedBirthDate = sharedPreferences.getString("birthDate", "");

        animalTypeSpinner.setSelection(animalTypeAdapter.getPosition(savedAnimalType));

        if (savedAnimalType.equals("Dog")) {
            breedSpinner.setAdapter(dogBreedAdapter);
        } else if (savedAnimalType.equals("Cat")) {
            breedSpinner.setAdapter(catBreedAdapter);
        } else {
            // 만약 savedAnimalType 값이 "Dog" 또는 "Cat" 이외의 값이면 기본값으로 Dog 품종 어댑터 설정
            breedSpinner.setAdapter(dogBreedAdapter);
        }

        // breedSpinner의 선택된 위치 설정 (이제 여기서 NPE가 발생하지 않아야 합니다.)
        breedSpinner.setSelection(((ArrayAdapter<String>) breedSpinner.getAdapter()).getPosition(savedBreed));
        spinnerGender.setSelection(genderAdapter.getPosition(savedGender));
        spinnerBloodType.setSelection(bloodTypeAdapter.getPosition(savedBloodType));
        editTextMemo.setText(savedMemo);
        editTextName.setText(savedName);
        editTextWeight.setText(savedWeight);
        editTextRegistrationNumber.setText(savedRegistrationNumber);
        editTextBirthDate.setText(savedBirthDate);


        // animalTypeSpinner의 선택된 위치 설정
        animalTypeSpinner.setSelection(animalTypeAdapter.getPosition(savedAnimalType));

        if (savedAnimalType.equals("Dog")) {
            breedSpinner.setAdapter(dogBreedAdapter);
        } else if (savedAnimalType.equals("Cat")) {
            breedSpinner.setAdapter(catBreedAdapter);
        } else {
            // 만약 savedAnimalType 값이 "Dog" 또는 "Cat" 이외의 값이면 기본값으로 Dog 품종 어댑터 설정
            breedSpinner.setAdapter(dogBreedAdapter);
        }

        // breedSpinner의 선택된 위치 설정 (이제 여기서 NPE가 발생하지 않아야 합니다.)
        breedSpinner.setSelection(((ArrayAdapter<String>)breedSpinner.getAdapter()).getPosition(savedBreed));
        editTextMemo.setText(savedMemo);

        // 저장 버튼 클릭 시 프로필 정보 저장
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("animalType", animalTypeSpinner.getSelectedItem().toString());
                editor.putString("breed", breedSpinner.getSelectedItem().toString());
                editor.putString("memo", editTextMemo.getText().toString());
                editor.putString("name", editTextName.getText().toString());
                editor.putString("gender", spinnerGender.getSelectedItem().toString());
                editor.putString("weight", editTextWeight.getText().toString());
                editor.putString("bloodType", spinnerBloodType.getSelectedItem().toString());
                editor.putString("registrationNumber", editTextRegistrationNumber.getText().toString());
                editor.putString("birthDate", editTextBirthDate.getText().toString());

                // 프로필 이미지 URI 저장
                if (selectedImage != null) {
                    editor.putString("imageURI", selectedImage.toString());
                }
                // 저장 변경 사항을 apply
                editor.apply();

                Toast.makeText(ProfileActivity.this, "Information saved!", Toast.LENGTH_SHORT).show();
                // 저장 후 메인 액티비티로 이동
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                
                // 현재 액티비티 종료
                finish();
            }
        });
    }

    // 권한 요청
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(android.content.Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    // 갤러리에서 사진 선택 시 이미지뷰에 사진 띄움
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // 권한을 영구적으로 유지합니다.
                getContentResolver().takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // 이미지 뷰에 선택된 이미지를 표시합니다.
                imageViewProfile.setImageURI(selectedImageUri);

                // SharedPreferences에 이미지 URI를 저장합니다.
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("imageURI", selectedImageUri.toString());
                editor.apply();
            }
        }
    }



    // 앱이 종료되었을 때 프로필 이미지 URI 저장
    @Override
    protected void onResume() {
        super.onResume();
        // 앱이 종료되었을 때 저장된 프로필 이미지 URI 불러오기
        try {
            String savedImageURI = sharedPreferences.getString("imageURI", "");
            if (!savedImageURI.isEmpty()) {
                List<UriPermission> perms = getContentResolver().getPersistedUriPermissions();
                boolean hasPerm = false;
                for (UriPermission perm : perms) {
                    if (perm.getUri().toString().equals(savedImageURI)) {
                        hasPerm = true;
                        break;
                    }
                }
                if (hasPerm) {
                    imageViewProfile.setImageURI(Uri.parse(savedImageURI));
                } else {
                    Toast.makeText(this, "No permission to load saved image", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to load saved image!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인되었을 때 이미지 선택 인텐트 시작 (옵션)
                pickImageFromGallery();
            } else {
                // 권한이 거부되었을 때 처리
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
