package ua.edu.viti.military.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.edu.viti.military.entity.HazardClass;
import ua.edu.viti.military.entity.SupplyCategory;
import ua.edu.viti.military.entity.SupplyItem;
import ua.edu.viti.military.entity.Warehouse;
import ua.edu.viti.military.repository.SupplyCategoryRepository;
import ua.edu.viti.military.repository.SupplyItemRepository;
import ua.edu.viti.military.repository.WarehouseRepository;

import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SupplyCategoryRepository categoryRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplyItemRepository itemRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            initData();
        }
    }

    private void initData() {
        SupplyCategory ammo = SupplyCategory.builder().name("Боєприпаси").description("Патрони, снаряди, гранати").build();
        SupplyCategory food = SupplyCategory.builder().name("Продовольство").description("Сухпайки, консерви, вода").build();
        SupplyCategory med = SupplyCategory.builder().name("Медикаменти").description("Аптечки, ліки").build();
        SupplyCategory fuel = SupplyCategory.builder().name("Паливно-мастильні матеріали").description("Бензин, дизель, мастила").build();
        SupplyCategory uniform = SupplyCategory.builder().name("Речове майно").description("Форма, взуття, спорядження").build();
        
        categoryRepository.saveAll(Arrays.asList(ammo, food, med, fuel, uniform));

        Warehouse mainDepot = Warehouse.builder().name("Центральний склад").location("Київ, Сектор А").capacity(10000).build();
        Warehouse fieldDepot = Warehouse.builder().name("Польовий склад #1").location("Східний напрямок").capacity(2000).build();
        Warehouse fuelDepot = Warehouse.builder().name("Склад ПММ").location("Житомирська обл.").capacity(50000).build();

        warehouseRepository.saveAll(Arrays.asList(mainDepot, fieldDepot, fuelDepot));

        SupplyItem ammo545 = SupplyItem.builder()
                .name("Патрони 5.45мм (цинк)")
                .quantity(500)
                .expirationDate(LocalDate.now().plusYears(10))
                .batchNumber("AM-2024-001")
                .hazardClass(HazardClass.EXPLOSIVE)
                .storageConditions("Сухе місце, t < 25C")
                .category(ammo)
                .warehouse(mainDepot)
                .build();

        SupplyItem stew = SupplyItem.builder()
                .name("Тушкована яловичина")
                .quantity(2000)
                .expirationDate(LocalDate.now().plusYears(2))
                .batchNumber("FD-2023-055")
                .hazardClass(HazardClass.NONE)
                .storageConditions("Звичайні")
                .category(food)
                .warehouse(fieldDepot)
                .build();

        SupplyItem diesel = SupplyItem.builder()
                .name("Дизельне паливо (Євро-5)")
                .quantity(10000)
                .expirationDate(LocalDate.now().plusYears(1))
                .batchNumber("FL-2024-101")
                .hazardClass(HazardClass.FLAMMABLE)
                .storageConditions("Резервуар підземний")
                .category(fuel)
                .warehouse(fuelDepot)
                .build();

        SupplyItem bandages = SupplyItem.builder()
                .name("Бинт стерильний 7х14")
                .quantity(5000)
                .expirationDate(LocalDate.now().plusYears(5))
                .batchNumber("MD-2023-999")
                .hazardClass(HazardClass.NONE)
                .storageConditions("Сухе місце")
                .category(med)
                .warehouse(fieldDepot)
                .build();

        SupplyItem boots = SupplyItem.builder()
                .name("Берці літні (Талан)")
                .quantity(150)
                .expirationDate(null)
                .batchNumber("UN-2024-005")
                .hazardClass(HazardClass.NONE)
                .storageConditions("Вентильоване приміщення")
                .category(uniform)
                .warehouse(mainDepot)
                .build();

        SupplyItem grenades = SupplyItem.builder()
                .name("Граната Ф-1")
                .quantity(200)
                .expirationDate(LocalDate.now().plusYears(15))
                .batchNumber("AM-2020-777")
                .hazardClass(HazardClass.EXPLOSIVE)
                .storageConditions("Сейф, окреме сховище")
                .category(ammo)
                .warehouse(mainDepot)
                .build();

        itemRepository.saveAll(Arrays.asList(ammo545, stew, diesel, bandages, boots, grenades));

        System.out.println("✅ Test data initialized successfully!");
    }
}
